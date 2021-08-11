package com.example.SSGPaymtCertProject.config;

import com.example.SSGPaymtCertProject.Service.user.LoginFailureHandler;
import com.example.SSGPaymtCertProject.Service.user.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @since 2021. 08. 11
 * @author kwon-yong-il
 *         <h3>스프링시큐리티 설정 자바파일</h3>
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상 인증을 통과한다. )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/font/**", "/resources/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("springboot").password("password").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 페이지 권한 설정
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/info").hasRole("MEMBER")
                .antMatchers("/**").permitAll()

                .and()// 로그인 설정
                .formLogin()
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailureHandler())
                .loginPage("/login")
                .usernameParameter("mbrLoginId")
                .passwordParameter("password")
                 // .loginProcessingUrl("/login/perform")
                .defaultSuccessUrl("/login/success")
                .failureUrl("/login?status=fail")

                .and() // 로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?status=logout")
                .invalidateHttpSession(true)

                .and() // 예외발생시 핸들링 여기선 로그인 페이지 가도록 설정
                .exceptionHandling().accessDeniedPage("/login?status=denied")

                .and() // 세션 설정
                .sessionManagement()
                .invalidSessionUrl("/login")
                .maximumSessions(1)
                .expiredUrl("/login");

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return null;
    }
}
