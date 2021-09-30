package com.app.animesoul.auth;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;


/**
 * @author satish sharma
 * <p>
 * Help with JWT related operations
 */
@Log4j2
@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsService customUserDetailsService;
    @Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app.jwtExpirationInMs}")
    private long jwtExpirationInMs;

    @Autowired
    public JwtTokenProvider(UserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /* @PostConstruct
       protected void init() {
            jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
        }
    */

    public String generateToken(Authentication authentication) {
        JwtUserDetail userPrincipal = (JwtUserDetail) authentication.getPrincipal();
        Date expiryDate = new Date(new Date().getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("ROLE", userPrincipal.getAuthorities())
                .claim("NAME", userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }


    public String getUserNameFromJWT(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(getUserNameFromJWT(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String getTokenValue(String authHeaderValue) {
        return (StringUtils.hasText(authHeaderValue) && authHeaderValue.startsWith("Bearer ")) ? authHeaderValue.substring("Bearer ".length()) : null;
    }
}

