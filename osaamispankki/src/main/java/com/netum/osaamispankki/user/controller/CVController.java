package com.netum.osaamispankki.user.controller;

import com.netum.osaamispankki.user.domain.Education;
import com.netum.osaamispankki.user.services.CVService;
import com.netum.osaamispankki.user.services.ErrorResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/")
public class CVController {
    @Autowired
    private ErrorResponseService errorResponseService;
    @Autowired
    private CVService cvService;

    @PostMapping("education")
    public ResponseEntity<?> saveEducation(@Valid @RequestBody Education[] educations, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return this.errorResponseService.getErrorResponse(bindingResult);
        }

        Iterable<Education> savedEducation = this.cvService.save(educations);

        return new ResponseEntity<>(savedEducation, HttpStatus.OK);
    }
}
