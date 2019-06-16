package com.netum.osaamispankki.controller;

import com.netum.osaamispankki.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/{businessId}")
    public ResponseEntity<?> fillCompanyInformation(@PathVariable String businessId) {
        companyService.getCompanyDataFromExternalSource(businessId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
