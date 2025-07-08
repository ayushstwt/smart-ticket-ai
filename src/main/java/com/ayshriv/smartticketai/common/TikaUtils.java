package com.ayshriv.smartticketai.common;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class TikaUtils {

    private static final Tika tika = new Tika();

    public static String extractText(MultipartFile file) {
        try {
            return tika.parseToString(file.getInputStream());
        } catch (IOException | TikaException e) {
            throw new RuntimeException("Failed to extract text using Tika", e);
        }
    }
}