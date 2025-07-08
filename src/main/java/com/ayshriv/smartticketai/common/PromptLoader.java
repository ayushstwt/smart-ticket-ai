package com.ayshriv.smartticketai.common;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PromptLoader {

    public static String loadFromClasspath(String filePath) {
        try {
            ClassPathResource resource = new ClassPathResource(filePath);
            byte[] bytes = resource.getInputStream().readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load prompt from classpath: " + filePath, e);
        }
    }
}