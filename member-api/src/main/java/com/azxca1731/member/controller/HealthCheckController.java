package com.azxca1731.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("hello")
    public String hello() {
        return "hello";
    }
}
