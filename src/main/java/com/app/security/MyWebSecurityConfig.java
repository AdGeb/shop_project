package com.app.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Configuration
@EnableWebSecurity
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    // metoda konfigurujaca uzytkownikow

    private UserDetailsService userDetailsService;

    public MyWebSecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // KONFIGURACJA USERA W PAMIECI
        /*
        auth
                .inMemoryAuthentication()
                .withUser("u").password("$2a$10$5LDIAqx4m0fLZbuVRCrJQOeouQxK3gckL4EMYpuRc4wnQXwetambC").roles("USER")
                .and()
                .withUser("a").password("$2a$10$5LDIAqx4m0fLZbuVRCrJQOeouQxK3gckL4EMYpuRc4wnQXwetambC").roles("USER", "ADMIN")
                .and()
                .withUser("s").password("$2a$10$5LDIAqx4m0fLZbuVRCrJQOeouQxK3gckL4EMYpuRc4wnQXwetambC").roles("USER", "ADMIN", "SUPER");
                */

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/producers/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/products/**").hasRole("ADMIN")
                .antMatchers("/", "/security/register", "/webjars/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/security/login").permitAll()
                .loginProcessingUrl("/app-login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/security/login/error")

                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())

                .and()
                .httpBasic();
    }

    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler() {
            @Override
            public void handle(
                    HttpServletRequest httpServletRequest,
                    HttpServletResponse httpServletResponse,
                    org.springframework.security.access.AccessDeniedException e) throws IOException, ServletException {
                httpServletResponse.sendRedirect("/security/accessDenied?message=" + e.getMessage());
            }
        };
    }
}
