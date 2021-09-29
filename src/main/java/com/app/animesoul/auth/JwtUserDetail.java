package com.app.animesoul.auth;


import com.app.animesoul.entities.User;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;


@ToString
public class JwtUserDetail implements UserDetails {
    private final User user;

    public JwtUserDetail(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority("ROLE_" + role.getName())
        ).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return Integer.parseInt(user.getStatus()) > 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Integer.parseInt(user.getStatus()) > 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Integer.parseInt(user.getStatus()) > 0;
    }

    @Override
    public boolean isEnabled() {
        return Integer.parseInt(user.getStatus()) > 0;
    }

    public User getUser() {
        return user;
    }
}
