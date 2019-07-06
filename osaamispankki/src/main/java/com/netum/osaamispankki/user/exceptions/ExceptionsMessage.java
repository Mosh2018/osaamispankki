package com.netum.osaamispankki.user.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionsMessage {
    COMPANY_ID_NOT_FOUND("company", "company id number not exist"),
    USERNAME_NOT_FOUND("username", "username not exist"),
    PROFILE_NOT_FOUND("profile", "profile not exist, add profile");

    private final String field;
    private final String message;

}
