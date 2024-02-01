package com.jwolbert.testablefunctions.annotation;
 
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jwolbert.testablefunctions.annotation.TestDataFile.TestData;
 
@Aspect
@Component
public class TestableAspect {
 
    @Around("@annotation(Testable)")
    public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {

        Gson gson = new Gson();
 
        System.out.println("Input :\n" + joinPoint.getArgs()[0]);
 
        Object result = joinPoint.proceed();

        System.out.println(joinPoint.getSignature());

        System.out.println(joinPoint.getSourceLocation());
 
        System.out.println(result);

        saveTest(
            joinPoint.getArgs()[0],
            result,
            "src/main/resources/tests.json"
        );
 
        return result;
    }

    @Value("classpath:tests.json")
    Resource resource;

    private void saveTest(
        Object input,
        Object output,
        String filePath
    ) throws StreamReadException, DatabindException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        TestDataFile testDataFile;

        if (resource.exists()) {
            
        }

        InputStream inputStream = resource.getInputStream();

        if (inputStream.readAllBytes().length > 0) {
            testDataFile = objectMapper.readValue(inputStream, TestDataFile.class);
        } else {
            testDataFile = new TestDataFile();
            testDataFile.setTestDataMap(new HashMap<>());
        } 

        inputStream.close();

        System.out.println(testDataFile.getTestDataMap().entrySet().size() + " current tests");

        Gson gson = new Gson();
        
        TestData testData = new TestData();

        testData.setInput(objectMapper.writeValueAsString(input));

        testData.setOutput(objectMapper.writeValueAsString(output));

        if (testDataFile.getTestDataMap().containsKey(filePath)) {
            testDataFile.getTestDataMap().get(
                filePath
            ).add(testData);
        } else {
            List<TestData> testDataList = new ArrayList<>();
            testDataList.add(testData);
            testDataFile.getTestDataMap().put(
                filePath,
                testDataList
            );
        }

        System.out.println(testDataFile.getTestDataMap().entrySet().size() + " current tests after add");

        FileWriter fileWriter = new FileWriter(filePath);

        gson.toJson(testDataFile, fileWriter);

        fileWriter.flush();

        fileWriter.close();
    }
 
}
