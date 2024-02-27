package com.example.demo.myproject.service.utils;

import java.io.IOException;
import java.util.Base64;

public class ImageUtils {

    public static String encodeFileToBase64String(byte[] fileContent) throws IOException {
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public static byte[] decodeBase64String(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }

}


