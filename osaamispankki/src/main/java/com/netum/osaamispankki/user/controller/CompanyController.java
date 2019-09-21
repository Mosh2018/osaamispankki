package com.netum.osaamispankki.user.controller;

import com.netum.osaamispankki.user.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/byCompanyName/{name}")
    public ResponseEntity<?> getCompaniesByName(@PathVariable String name) {
        return new ResponseEntity(companyService.getCompaniesByName(name), HttpStatus.OK);
    }

}
