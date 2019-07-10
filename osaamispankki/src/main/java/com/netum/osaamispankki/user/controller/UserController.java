package com.netum.osaamispankki.user.controller;

import com.netum.osaamispankki.security.UserLoginRequest;
import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.service.ErrorResponseService;
import com.netum.osaamispankki.user.service.UserService;
import com.netum.osaamispankki.user.validation.UserValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

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
        return new ResponseEntity<>(userService.createUser(newUser), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest loginRequest, BindingResult result) {
        if (result.hasErrors()) {
            return errorResponseService.getErrorResponse(result);
        }
        return new ResponseEntity<>(userService.loginJwt(loginRequest), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable("username") String username) {
        return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
    }


    @PostMapping("/addCompanyToUser/{username}") // todo move this to company controller
    public ResponseEntity<?> addCompanyToUser(@PathVariable String username, @RequestBody String companyName) {
        return new ResponseEntity<>(userService.saveNewCompany(username, companyName), HttpStatus.OK);
    }
}
