package com.netum.osaamispankki.user.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class UtilsMethods {

    public static String setExceptionMessage(String field, String message) {
        return field + ": " + message;
    }


    public static ResponseEntity<Object> getResponsiveError(RuntimeException ex, HttpStatus status) {
        String key = ":";
        int index = ex.getMessage().indexOf(key);
        String field = ex.getMessage().substring(0, index);
        String message = ex.getMessage().substring(index +2);
        Map<String, String> error = new HashMap<>();
        error.put(field, message);
        return new ResponseEntity<>(error, status);
    }

    public static boolean isBlank(String x) {
        return x == null || x.isEmpty();
    }

    public static boolean notBlank(String x) {
        return !isBlank(x);
    }

    public static boolean _false(Boolean x) {
        return x==false;
    }
}
