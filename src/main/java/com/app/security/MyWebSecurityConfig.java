package com.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    // metoda konfigurujaca uzytkownikow

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("u").password("$2a$10$5LDIAqx4m0fLZbuVRCrJQOeouQxK3gckL4EMYpuRc4wnQXwetambC").roles("USER")
                .and()
                .withUser("a").password("$2a$10$5LDIAqx4m0fLZbuVRCrJQOeouQxK3gckL4EMYpuRc4wnQXwetambC").roles("USER", "ADMIN")
                .and()
                .withUser("s").password("$2a$10$5LDIAqx4m0fLZbuVRCrJQOeouQxK3gckL4EMYpuRc4wnQXwetambC").roles("USER", "ADMIN", "SUPER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/producers/**").hasRole("USER")
                .antMatchers("/products/**").hasRole("ADMIN")
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()

                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
