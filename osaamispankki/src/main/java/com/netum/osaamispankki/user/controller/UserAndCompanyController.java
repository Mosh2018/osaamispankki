package com.netum.osaamispankki.user.controller;


import com.netum.osaamispankki.user.common.GenericHelper;
import com.netum.osaamispankki.user.domain.UserCompany;
import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.services.ErrorResponseService;
import com.netum.osaamispankki.user.services.UserAndCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.netum.osaamispankki.user.common.UtilsMethods.setExceptionMessage;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/userAndCompany")
public class UserAndCompanyController {

    @Autowired
    private UserAndCompanyService userAndCompanyService;

    @Autowired
    private ErrorResponseService errorResponseService;

    @GetMapping("/companies")
    public ResponseEntity<?> getUserCompanies() {
        return new ResponseEntity<>(userAndCompanyService.getUserCompanies(), HttpStatus.OK);
    }

    @PostMapping("/company")
    public ResponseEntity<?> addCompany(@Valid @RequestBody UserCompany userCompany, BindingResult result) {
        if ( result.hasErrors()) {
            return errorResponseService.getErrorResponse(result);
        }
        UserCompany company = userAndCompanyService.addOrSaveCompany(userCompany);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<?> addCompany(@PathVariable Long id) {
        if (GenericHelper.isNull(id)) {
            throw new OsaamispankkiException(setExceptionMessage("company", "company not found"));
        }
        return new ResponseEntity<>(this.userAndCompanyService.deleteCompany(id), HttpStatus.OK);
    }

    // save one company by id
    // delete one company by id
    // delete all user companies
}
