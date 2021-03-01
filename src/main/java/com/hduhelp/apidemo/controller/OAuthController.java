package com.hduhelp.apidemo.controller;

import com.hduhelp.apidemo.api.info.InfoAPI;
import com.hduhelp.apidemo.model.BaseResponse;
import com.hduhelp.apidemo.api.oauth.model.TokenResponse;
import com.hduhelp.apidemo.api.oauth.model.ValidateResponse;
import com.hduhelp.apidemo.api.oauth.OAuthAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
public class OAuthController {
    private  final Logger logger = LoggerFactory.getLogger(OAuthController.class);

    private final OAuthAPI mOAuthAPI;

    public OAuthController(OAuthAPI mOAuthAPI) {
        this.mOAuthAPI = mOAuthAPI;
    }

    @RequestMapping(value="/",method = RequestMethod.GET)
    public String getHost(){
        return "hello"+ mOAuthAPI.getHost();
    }

    @RequestMapping(value="/request",method = RequestMethod.GET)
    public void requestAuth(HttpServletResponse response) throws IOException {
        String authUrl= mOAuthAPI.getAuthUrl("this_is_a_state_to_prevent_csrf");
        response.sendRedirect(authUrl);
    }

    @RequestMapping(value = "/callback",method = RequestMethod.GET)
    public Object callback(@RequestParam(name = "code") String code,
                                 @RequestParam(name = "state") String state) throws IOException {
        logger.debug("code:" + code + " state:" + state);
        String accessToken = null;
        TokenResponse tokenResponse = mOAuthAPI.getAccessToken(code);
        logger.debug(tokenResponse.toString());
        if (!tokenResponse.isSuccess()) {
            return BaseResponse.create(50000,"failed to get token");
        }
        ValidateResponse validateResponse = mOAuthAPI.validate(tokenResponse.getAccessToken());
        if (!validateResponse.isValid()) {
            return BaseResponse.create(40100,"invalid token");
        }
        logger.debug(validateResponse.toString());
        accessToken = validateResponse.getAccessToken();
        return InfoAPI.getStudentInfo(accessToken);
    }

}
