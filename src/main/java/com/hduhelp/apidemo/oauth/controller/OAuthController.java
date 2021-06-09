package com.hduhelp.apidemo.oauth.controller;

import com.hduhelp.apidemo.info.InfoAPI;
import com.hduhelp.apidemo.info.model.PersonInfoResponse;
import com.hduhelp.apidemo.oauth.controller.model.TokenResponse;
import com.hduhelp.apidemo.oauth.controller.model.ValidateResponse;
import com.hduhelp.apidemo.oauth.service.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/oauth")
public class OAuthController {
    private final Logger logger = LoggerFactory.getLogger(OAuthController.class);

    private static final String KEY_OAUTH_STATE="oauth_state";

    private final OAuthService mOAuthService;

    public OAuthController(OAuthService oAuthService) {
        this.mOAuthService = oAuthService;
    }

    @RequestMapping(value="/",method = RequestMethod.GET)
    public String getHost(){
        return "hello"+ mOAuthService.getHost();
    }

    @GetMapping(value="/request")
    public void requestAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session= request.getSession();
        String state= UUID.randomUUID().toString();
        session.setAttribute(KEY_OAUTH_STATE,state);
        String authUrl= mOAuthService.getAuthUrl(state);
        response.sendRedirect(authUrl);
    }

    @RequestMapping(value = "/callback",method = RequestMethod.GET)
    public void callback(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam(name = "code") String code,
                                           @RequestParam(name = "state") String state,
                         @RequestParam(value = "serverVerify",required = false) boolean serverVerify)
            throws IOException,IllegalArgumentException, IllegalAccessException {
        logger.debug("code:" + code + " state:" + state);
        HttpSession session = request.getSession();
        if(!serverVerify) { //通过助手token直接走callback流程
            Optional<Object> stateOptional = Optional.ofNullable(session.getAttribute(KEY_OAUTH_STATE));
            String sessionState = stateOptional.orElse("optional-state").toString();
            if (!sessionState.equals(state)) { //校验state
                throw new IllegalArgumentException("unmatched state");
            }
        }
        String accessToken;
        TokenResponse tokenResponse = mOAuthService.getAccessToken(code,state);
        logger.debug(tokenResponse.toString());
        if (!tokenResponse.isSuccess()) {
           throw new IllegalAccessException("failed to get token");
        }
        ValidateResponse validateResponse = mOAuthService.validate(tokenResponse.getAccessToken());
        if (!validateResponse.isValid()) {
           throw new RuntimeException("invalid token");
        }
        logger.debug(validateResponse.toString());
        accessToken = validateResponse.getAccessToken();
        PersonInfoResponse personInfoResponse= InfoAPI.getPersonInfo(accessToken);
        session.setAttribute(Const.KEY_STAFF_ID,personInfoResponse.staffID);
        response.sendRedirect(mOAuthService.getCallbackRedirectUrl());
    }

}
