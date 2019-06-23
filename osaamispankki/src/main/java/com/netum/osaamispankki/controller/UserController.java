package com.netum.osaamispankki.controller;

import com.netum.osaamispankki.domain.User;
import com.netum.osaamispankki.service.ErrorResponseService;
import com.netum.osaamispankki.service.UserService;
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

    @PostMapping("/add_new_user")
    public ResponseEntity<?> addingNewUser(@Valid @RequestBody User newUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return errorResponseService.getErrorResponse(bindingResult);
        }
        User user = userService.addNewUser(newUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable("username") String username) {
        User user = userService.getUser(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
//
//    @PutMapping("/{username}")
//    public ResponseEntity<?> updateUser(@PathVariable("username") String username, @Valid @RequestBody User updatedUser, BindingResult bindingResult) {
//
//        if (bindingResult.hasErrors()) {
//            return errorResponseService.getErrorResponse(bindingResult);
//        }
//        return new ResponseEntity<>(userService.updateUser(updatedUser), HttpStatus.OK);
//    }
}
