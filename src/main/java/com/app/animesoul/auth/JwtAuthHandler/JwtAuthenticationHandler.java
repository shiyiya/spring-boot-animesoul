package com.app.animesoul.auth.JwtAuthHandler;

import com.app.animesoul.payload.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 401
 */
@Component
public class JwtAuthenticationHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().println(new ObjectMapper().writeValueAsString(ApiResponse.x(HttpServletResponse.SC_UNAUTHORIZED, e.getLocalizedMessage())));
    }
}