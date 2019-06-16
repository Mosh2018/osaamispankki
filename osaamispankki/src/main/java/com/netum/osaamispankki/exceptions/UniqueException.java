package com.netum.osaamispankki.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UniqueException extends RuntimeException{
    public UniqueException(String message) {
        super(message);
    }
}
