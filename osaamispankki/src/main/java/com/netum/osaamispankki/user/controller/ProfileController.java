package com.netum.osaamispankki.user.controller;

import com.netum.osaamispankki.user.domain.Profile;
import com.netum.osaamispankki.user.service.ErrorResponseService;
import com.netum.osaamispankki.user.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ErrorResponseService errorResponseService;

    @PostMapping("/{username}")
    public ResponseEntity<?> addUserProfile(@PathVariable("username") String username,
                                            @Valid @RequestBody Profile newProfile, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
          return errorResponseService.getErrorResponse(bindingResult);
        }
        return new ResponseEntity<>(this.profileService.createProfile(newProfile, username), HttpStatus.CREATED);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUserProfile(@PathVariable("username") String username,
                                               @Valid @RequestBody Profile newProfile, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return errorResponseService.getErrorResponse(bindingResult);
        }
        return new ResponseEntity<>(this.profileService.updateProfile(newProfile, username), HttpStatus.OK);
    }


    @GetMapping("/{username}")
    public ResponseEntity<?> getProfileByUsername(@PathVariable("username") String username) {
        return new ResponseEntity<>(profileService.getProfileByUsername(username), HttpStatus.OK);
    }

}
