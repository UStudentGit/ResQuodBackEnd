package com.ustudent.resquod.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/user/login*", "/user/register*", "/test1");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/test1", "/").permitAll()
                .antMatchers("/test2").authenticated()
                .antMatchers("/test3").hasRole("ADMIN")
                .and().addFilter(new JwtFilter(authenticationManager()))
                .csrf().disable();
    }

}
