package com.achobeta.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
@RequiredArgsConstructor
public class TokenAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByUsername(token);
        // 根据令牌验证用户身份
        if (isValidToken(token)) {
            // 创建一个包含用户权限的Authentication对象
            // 这可以是UsernamePasswordAuthenticationToken或其他适当的子类
            return new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>());
        } else {
            throw new BadCredentialsException("Invalid token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private boolean isValidToken(String token) {
        // 实现令牌验证逻辑
        return true;
    }
}
