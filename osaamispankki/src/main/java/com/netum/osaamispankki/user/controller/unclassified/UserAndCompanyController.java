package com.netum.osaamispankki.user.controller.unclassified;


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

import java.util.Set;

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
        Set<UserCompany> companies = deleteCompanyCodeFromResponse(userAndCompanyService.getUserCompanies());
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @PostMapping("/company")
    public ResponseEntity<?> addCompany(@Valid @RequestBody UserCompany userCompany, BindingResult result) {
        if ( result.hasErrors()) {
            return errorResponseService.getErrorResponse(result);
        }
        UserCompany company = deleteCompanyCodeFromResponse(userAndCompanyService.addOrSaveCompany(userCompany));
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<?> addCompany(@PathVariable Long id) {
        if (GenericHelper.isNull(id)) {
            throw new OsaamispankkiException(setExceptionMessage("company", "company not found"));
        }
        return new ResponseEntity<>(this.userAndCompanyService.deleteCompany(id), HttpStatus.OK);
    }

    private Set<UserCompany> deleteCompanyCodeFromResponse(Set<UserCompany> userCompanies) {

        for (UserCompany usercompany :userCompanies) {
            deleteCompanyCodeFromResponse(usercompany);

        }
        return userCompanies;
    }
    private UserCompany deleteCompanyCodeFromResponse(UserCompany userCompany){
      userCompany.setCompanyCode(null);
      return userCompany;
    }
}
