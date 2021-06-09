package com.hduhelp.apidemo.login;

import com.hduhelp.apidemo.common.response.BaseResponse;
import com.hduhelp.apidemo.oauth.controller.Const;
import com.hduhelp.apidemo.utils.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping(value = "/self",method = RequestMethod.GET)
    public ResponseEntity<BaseResponse<String>> getSelf(HttpServletRequest request)
            throws IllegalAccessException{
        HttpSession session=request.getSession();
        Optional<Object> staffIDOptional= Optional.ofNullable(session.getAttribute(Const.KEY_STAFF_ID));
        if(!staffIDOptional.isPresent()){
            throw new IllegalAccessException("please login first");
        }
        String staffID=staffIDOptional.orElse("unexpected").toString();
        return ResponseUtils.responseInController(HttpStatus.OK,
                BaseResponse.createSuccess(staffID));
    }
}
