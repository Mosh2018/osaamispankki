package com.netum.osaamispankki.user.exceptions;

public class PasswordsDoesNotMatch extends RuntimeException{
    public PasswordsDoesNotMatch(String message) {
        super(message);
    }
}
