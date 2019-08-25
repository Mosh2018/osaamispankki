package com.netum.osaamispankki.user.controller;

import com.netum.osaamispankki.user.domain.Education;
import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.modals.UserProfile;
import com.netum.osaamispankki.user.services.BasicInformationService;
import com.netum.osaamispankki.user.services.CVService;
import com.netum.osaamispankki.user.services.ErrorResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import static com.netum.osaamispankki.user.common.UtilsMethods.setExceptionMessage;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/basic")
public class BasicInformationController {

    @Autowired
    private ErrorResponseService errorResponseService;

    @Autowired
    private BasicInformationService basicInformationService;

    @Autowired
    private CVService cvService;

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


    @PostMapping("/education")
    public ResponseEntity<?> saveEducation(@Valid @RequestBody Education educations, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return this.errorResponseService.getErrorResponse(bindingResult);
        }
        try {
            return new ResponseEntity<>(this.cvService.save(educations), HttpStatus.OK);
        } catch (Exception e) {
            throw new OsaamispankkiException(
                    setExceptionMessage("adding_education", "can't add education " + e.getMessage()));
        }

    }

    @GetMapping("/education")
    public ResponseEntity<?> getEducations() {
        try {
            return new ResponseEntity(this.cvService.getEducations(), HttpStatus.OK);
        } catch (Exception e) {
            throw new OsaamispankkiException(
                    setExceptionMessage("adding_education", "can't get educations " + e.getMessage()));
        }
    }

    @DeleteMapping("education/{id}")
    public ResponseEntity<?> deleteEducation(@PathVariable Long id) {
        try {
            this.cvService.deleteEducation(id);
            return new ResponseEntity(true, HttpStatus.OK);
        } catch (Exception e) {
            throw new OsaamispankkiException(
                    setExceptionMessage("delete_education", "can't delete education " + e.getMessage()));
        }
    }
}
