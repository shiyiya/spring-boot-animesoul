package com.app.animesoul.auth;


import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author satish sharma
 * <p>
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
public class JwtFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtJwtTokenProvider;

    public JwtFilter(JwtTokenProvider jwtJwtTokenProvider) {
        this.jwtJwtTokenProvider = jwtJwtTokenProvider;
    }

    /**
     * Extract Authorization header and validate token.
     * IF token is valid the set the {@link UserPrincipal}
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        String token = jwtJwtTokenProvider.getTokenValue(((HttpServletRequest) req).getHeader(HttpHeaders.AUTHORIZATION));

        Logger.getGlobal().info("doFilter token: " + token);
        if (token != null && jwtJwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtJwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(req, res);
    }
}