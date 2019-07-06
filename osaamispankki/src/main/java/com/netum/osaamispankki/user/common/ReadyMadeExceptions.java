package com.netum.osaamispankki.user.common;

import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;

import static com.netum.osaamispankki.user.common.UtilsMethods.setExceptionMessage;
import static com.netum.osaamispankki.user.exceptions.ExceptionsMessage.COMPANY_ID_NOT_FOUND;
import static com.netum.osaamispankki.user.exceptions.ExceptionsMessage.USERNAME_NOT_FOUND;

public class ReadyMadeExceptions {

    public static OsaamispankkiException userNotFoundException() {
        return new OsaamispankkiException(setExceptionMessage(USERNAME_NOT_FOUND.getField(), USERNAME_NOT_FOUND.getMessage()));
    }

    public static OsaamispankkiException companyNotFoundException() {
        return new OsaamispankkiException(setExceptionMessage(COMPANY_ID_NOT_FOUND.getField(), COMPANY_ID_NOT_FOUND.getMessage()));
    }
}
