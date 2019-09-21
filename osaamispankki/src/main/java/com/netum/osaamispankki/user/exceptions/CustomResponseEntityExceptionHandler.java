package com.netum.osaamispankki.user.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.RollbackException;

import static com.netum.osaamispankki.user.common.UtilsMethods.getResponsiveError;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> passwordsNotMatchesException(PasswordsDoesNotMatch ex) {
        return getResponsiveError(ex, BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> userHasBeenUsedException(UserFoundCanNotAddAsNewUser ex) {
        return getResponsiveError(ex, BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUniqueException(UniqueException ex) {
        return getResponsiveError(ex, BAD_REQUEST);
    }

    @ExceptionHandler
    public final  ResponseEntity<Object> osaamispankkiError(OsaamispankkiException ex) {
        return getResponsiveError(ex, FORBIDDEN);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> personalInformationError(RollbackException ex) {
        return getResponsiveError(ex, FORBIDDEN);
    }

}
