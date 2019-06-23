package com.netum.osaamispankki.exceptions;

public class PasswordsDoesNotMatch extends RuntimeException{
    public PasswordsDoesNotMatch(String message) {
        super(message);
    }
}
