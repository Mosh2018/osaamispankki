package com.netum.osaamispankki.user.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

public class GenericHelper<T> {
    public static <T>  Set<T> tSet(T x) {
        Set<T> set = new HashSet<>();
        set.add(x);
        return set;
    }

    public static <T> boolean notNull(T x) {
        return x != null;
    }

    public static <T> boolean isNull(T x) {
        return x == null;
    }

    public static <T> ResponseEntity successResponse(T x) {
        return new ResponseEntity(x, HttpStatus.OK);
    }
}
