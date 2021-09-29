package com.app.animesoul.config;

import com.app.animesoul.auth.JwtAuthHandler.JwtAccessDeniedHandler;
import com.app.animesoul.auth.JwtAuthHandler.JwtAuthenticationHandler;
import com.app.animesoul.auth.JwtConfigurer;
import com.app.animesoul.auth.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtAuthenticationHandler jwtAuthenticationHandler;

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService)
                .passwordEncoder(encoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/webjars/**")
                .antMatchers("/static/**")
                .antMatchers("/");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationHandler)
                .and().authorizeRequests()
//                .antMatchers("/auth/**").permitAll()
//                .antMatchers("/user/checkUsernameAvailability")
//                .permitAll()
                .antMatchers("/0x00/**", "/druid").hasAnyRole("root", "admin")
                .anyRequest().permitAll()
//                .anyRequest().authenticated()// Rest of the request must be authenticated
                .and().apply(new JwtConfigurer(jwtTokenProvider))
        ;
        http.exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler);

    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
