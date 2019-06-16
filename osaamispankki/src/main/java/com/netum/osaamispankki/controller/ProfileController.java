package com.netum.osaamispankki.controller;

import com.netum.osaamispankki.domain.Profile;
import com.netum.osaamispankki.service.ErrorResponseService;
import com.netum.osaamispankki.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ErrorResponseService errorResponseService;

    @PostMapping("")
    public ResponseEntity<?> createNewProfile(@Valid @RequestBody Profile newProfile, BindingResult bindingResult) {

        if (bindingResult.getFieldErrors().size() != 0){
          return errorResponseService.getErrorResponse(bindingResult);
        }
        Profile profile = this.profileService.createOrUpdateProfile(newProfile);
        return new ResponseEntity<>(profile, HttpStatus.CREATED);
    }

}
