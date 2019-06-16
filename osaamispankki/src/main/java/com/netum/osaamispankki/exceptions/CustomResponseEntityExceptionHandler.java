package com.netum.osaamispankki.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestController
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleUniqueException(UniqueException ex, WebRequest request) {
        Map<String, String> error = getResponsiveError(ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final  ResponseEntity<Object> osaamispankkiError(OsaamispankkiException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("osaamispankki_error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    private Map<String, String> getResponsiveError(UniqueException ex) {
        String key = ":";
        int index = ex.getMessage().indexOf(key);
        String field = ex.getMessage().substring(0, index);
        String message = ex.getMessage().substring(index +2);
        Map<String, String> error = new HashMap<>();
        error.put(field, message);
        return error;
    }
}
