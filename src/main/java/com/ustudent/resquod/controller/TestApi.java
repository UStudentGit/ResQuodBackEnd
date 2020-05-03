package com.ustudent.resquod.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {
    @GetMapping("/test1")
    public String test1() {
        return "test1";
    }

    @GetMapping("/test2")
    @ApiOperation(value = "Require user role", authorizations = {@Authorization(value = "authkey")})
    public String test2() {
        return "test2";
    }

    @GetMapping("/test3")
    @ApiOperation(value = "Require admin role", authorizations = {@Authorization(value = "authkey")})
    public String test3() {
        return "test3";
    }
}
