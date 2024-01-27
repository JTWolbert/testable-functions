package com.jwolbert.testablefunctions.controller;

import org.springframework.web.bind.annotation.RestController;

import com.jwolbert.testablefunctions.service.ITestService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
public class TestController {
    
    private final ITestService testService;
    @GetMapping("test")
    public String requestMethodName(@RequestParam String param) {
        return testService.process(param);
    }
}
