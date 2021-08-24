package com.example.SSGPaymtCertProject.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @since 2021. 08. 11
 * @author kwon-yong-il
 *         <h3>스프링시큐리티 Custom SSG스터디용 AuthenticationProvider구현체</h3>
 */
@Component
public class SsgUserAuthProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        String loginId = (String) authentication.getPrincipal();
        String loginPwd = (String)authentication.getCredentials();
        String hashedLoginPwd = passwordEncoder.encode(loginPwd);

        UserDetails user = userService.loadUserByUsername(loginId);

        if (user == null || !passwordEncoder.matches(loginPwd, user.getPassword())) {
            throw new BadCredentialsException(loginId);
        }

        return new UsernamePasswordAuthenticationToken(loginId, hashedLoginPwd, user.getAuthorities());
    }

    /**
     * 인증 토큰 정합성
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
