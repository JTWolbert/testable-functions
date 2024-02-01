package com.jwolbert.testablefunctions.annotation;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class TestDataFile {

    Map<String, List<TestData>> testDataMap;
    
    @Data
    public static class TestData {
    
        String input;
        String output;
    }
}
