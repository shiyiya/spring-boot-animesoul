package com.app.animesoul.auth;


import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtJwtTokenProvider;

    public JwtFilter(JwtTokenProvider jwtJwtTokenProvider) {
        this.jwtJwtTokenProvider = jwtJwtTokenProvider;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = jwtJwtTokenProvider.getTokenValue(request.getHeader(HttpHeaders.AUTHORIZATION));
        Logger.getGlobal().info("JwtFilter doFilterInternal token: " + authToken);

        if (authToken != null
                && jwtJwtTokenProvider.validateToken(authToken)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken auth = jwtJwtTokenProvider.getAuthentication(authToken);
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }

    /**
     * Extract Authorization header and validate token.
     * IF token is valid the set the {@link JwtUserDetail}
     */
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
//            throws IOException, ServletException {
//
//        String token = jwtJwtTokenProvider.getTokenValue(((HttpServletRequest) req).getHeader(HttpHeaders.AUTHORIZATION));
//
//        Logger.getGlobal().info("doFilter token: " + token);
//        if (token != null && jwtJwtTokenProvider.validateToken(token)) {
//            Authentication auth = jwtJwtTokenProvider.getAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }
//        filterChain.doFilter(req, res);
//    }
}