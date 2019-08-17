package com.netum.osaamispankki.user.controller;

import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.modals.UserProfile;
import com.netum.osaamispankki.user.services.BasicInformationService;
import com.netum.osaamispankki.user.services.ErrorResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.netum.osaamispankki.user.common.UtilsMethods.setExceptionMessage;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/basic")
public class BasicInformationController {

    @Autowired
    private ErrorResponseService errorResponseService;

    @Autowired
    private BasicInformationService basicInformationService;

    @PostMapping("/profile")
    public ResponseEntity<?> saveUserProfile(@Valid @RequestBody UserProfile userProfile, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return this.errorResponseService.getErrorResponse(bindingResult);
        }
        try {
            UserProfile userProfileResponse = this.basicInformationService.saveHomeAddress(userProfile);
            return new ResponseEntity<>(userProfileResponse, HttpStatus.OK);
        } catch (Exception e) {
            throw new OsaamispankkiException(
                   setExceptionMessage("personal_information", "can't save address "));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getPersonalInformation() {

        try {
            return new ResponseEntity(this.basicInformationService.getPersonalInformation(), HttpStatus.OK);
        } catch (Exception e) {
            throw new OsaamispankkiException(
                    setExceptionMessage("personal_information", "can't get user information" + e.getMessage()));
        }
    }
}
