package com.jwolbert.testablefunctions.service.impl;

import org.springframework.stereotype.Service;

import com.jwolbert.testablefunctions.annotation.Testable;
import com.jwolbert.testablefunctions.service.ITestService;

@Service
public class TestService implements ITestService {
    
    @Override
    @Testable
    public String process(String param) {

        return param + "processed";
    }
}
