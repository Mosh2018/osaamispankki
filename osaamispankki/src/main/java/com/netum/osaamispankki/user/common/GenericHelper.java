package com.netum.osaamispankki.user.common;

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
}
