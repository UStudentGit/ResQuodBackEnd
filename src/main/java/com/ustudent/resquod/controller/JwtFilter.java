package com.ustudent.resquod.controller;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ustudent.resquod.service.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class JwtFilter implements javax.servlet.Filter {
    private JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String header = httpServletRequest.getHeader("authorization");
        if (header == null || !header.startsWith("Bearer ")) throw new ServletException("Wrong or empty token");
        else {
            String token = header.substring(7);
            DecodedJWT decodedJWT = jwtService.verify(token);
            Map<String, Claim> claims = decodedJWT.getClaims();
            servletRequest.setAttribute("claims", claims);
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
