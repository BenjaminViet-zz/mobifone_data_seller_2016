package com.benluck.vms.mobifonedataseller.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyRememberMeServices extends TokenBasedRememberMeServices {

    public MyRememberMeServices(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }

    @Override
    protected void setCookie(String[] tokens,
                         int maxAge,
                         HttpServletRequest request,
                         HttpServletResponse response) {

        super.setCookie(tokens, maxAge, request, response);
    }

}
