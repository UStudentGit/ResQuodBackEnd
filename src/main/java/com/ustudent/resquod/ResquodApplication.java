package com.ustudent.resquod;

import com.ustudent.resquod.controller.JwtFilter;
import com.ustudent.resquod.service.JwtService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@SpringBootApplication
public class ResquodApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResquodApplication.class, args);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtService jwtService() {
        return new JwtService("resquod");
    }

    //TODO reformat code and create packages!
    @Bean
    public FilterRegistrationBean<JwtFilter> filterRegistrationBean() {
        FilterRegistrationBean<JwtFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new JwtFilter(jwtService()));
        filterRegistrationBean.setUrlPatterns(Collections.singleton("/test2/*"));
        return filterRegistrationBean;
    }

}
