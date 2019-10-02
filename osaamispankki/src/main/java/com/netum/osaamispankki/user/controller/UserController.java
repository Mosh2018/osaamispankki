package com.netum.osaamispankki.user.controller;

import com.netum.osaamispankki.security.UserLoginRequest;
import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.services.ErrorResponseService;
import com.netum.osaamispankki.user.service.LoginService;
import com.netum.osaamispankki.user.services.UserService;
import com.netum.osaamispankki.user.validation.FrontendValidations;
import com.netum.osaamispankki.user.validation.UserValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.netum.osaamispankki.user.common.UtilsMethods.isBlank;
import static com.netum.osaamispankki.user.common.UtilsMethods.setExceptionMessage;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4204"})
@RequestMapping("/api/user")
public class UserController {

    @Autowired private LoginService loginService;

    @Autowired private UserService userService;

    @Autowired
    private ErrorResponseService errorResponseService;

    @Autowired
    private UserValidations userValidations;

    @PostMapping("/add_new_user")
    public ResponseEntity<?> addingNewUser(@Valid @RequestBody User newUser, BindingResult bindingResult) {
        userValidations.validate(newUser, bindingResult);
        if (bindingResult.hasErrors()) {
            return errorResponseService.getErrorResponse(bindingResult);
        }
        try {
            return new ResponseEntity<>(userService.createUser(newUser), HttpStatus.OK);
        } catch (Exception e) {
            throw new OsaamispankkiException(setExceptionMessage("sign_in", "can not create a user"));
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest loginRequest, BindingResult result) {
        if (result.hasErrors()) {
            return errorResponseService.getErrorResponse(result);
        }
        return new ResponseEntity<>(loginService.loginJwt(loginRequest), HttpStatus.OK);
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        if (isBlank(username)) {
            throw new OsaamispankkiException(setExceptionMessage("username",  "username can not by null"));
        }

        return new ResponseEntity<>(loginService.getUser(username), HttpStatus.OK);
    }


    @GetMapping("/confirm-account")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmId) {
        if(isBlank(confirmId)) {
            throw new OsaamispankkiException(setExceptionMessage("confirmation_id", "The link is invalid or broken!"));
        }

        return new ResponseEntity<>(loginService.confirmUserAccount(confirmId),HttpStatus.OK);
    }

    @GetMapping("/validations")
    public ResponseEntity<?> getUserSettings() {
        return new ResponseEntity<>(new FrontendValidations(), HttpStatus.OK);
    }
}
