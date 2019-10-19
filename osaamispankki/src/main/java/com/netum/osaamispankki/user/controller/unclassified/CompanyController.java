package com.netum.osaamispankki.user.controller.unclassified;

import com.netum.osaamispankki.user.common.GenericHelper;
import com.netum.osaamispankki.user.domain.Company;
import com.netum.osaamispankki.user.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/{businessId}")
    public ResponseEntity<?> fillCompanyInformation(@PathVariable String businessId) {
        return new ResponseEntity<>(filterApiResponse(companyService.getCompanyDataFromExternalSource(businessId)), HttpStatus.OK);
    }

    @GetMapping("/byCompanyName/{name}")
    public ResponseEntity<?> getCompaniesByName(@PathVariable String name) {
        return new ResponseEntity(companyService.getCompaniesByName(name), HttpStatus.OK);
    }

    @GetMapping("/createdPerson")
    public ResponseEntity<?> getCompanyByPerson() {
        return new ResponseEntity(filterApiResponse(companyService.getCompanyByLoggedUser()), HttpStatus.OK);
    }

    private Company filterApiResponse(Company company) {
        if (GenericHelper.notNull(company)) {
            company.setCreatedBy(0L);
            company.setId(0L);
            return company;
        }
        return null;
    }
}
