package com.netum.osaamispankki.user.common;

import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    public static OsaamispankkiException throwException(String field, String mes) {
        return new OsaamispankkiException(setExceptionMessage(field, mes));
    }

    public static int calculatePasswordStrength(String password) {

        int iPasswordScore = 0;

        if( password.length() < 8 )
            return 0;
        else if( password.length() >= 10 )
            iPasswordScore += 2;
        else
            iPasswordScore += 1;

        //if it contains one digit, add 2 to total score
        if( password.matches("(?=.*[0-9]).*") )
            iPasswordScore += 2;

        //if it contains one lower case letter, add 2 to total score
        if( password.matches("(?=.*[a-z]).*") )
            iPasswordScore += 2;

        //if it contains one upper case letter, add 2 to total score
        if( password.matches("(?=.*[A-Z]).*") )
            iPasswordScore += 2;

        //if it contains one special character, add 2 to total score
        if( password.matches("(?=.*[~!@#$%^&*()_-]).*") )
            iPasswordScore += 2;

        return iPasswordScore;
    }

    public static Date stringToDateConverter(String format, String dateAsString) {
        try {
            return  new SimpleDateFormat(format).parse(dateAsString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String generateUUIDString() {
        return UUID.randomUUID().toString();
    }
}
