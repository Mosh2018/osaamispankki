package com.netum.osaamispankki.user.controller.unclassified;

import com.netum.osaamispankki.user.common.GenericHelper;
import com.netum.osaamispankki.user.domain.Company;
import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.netum.osaamispankki.user.common.GenericHelper.notNull;
import static com.netum.osaamispankki.user.common.GenericHelper.successResponse;
import static com.netum.osaamispankki.user.common.UtilsMethods.setExceptionMessage;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/{businessId}/code/{activationCode}")
    public ResponseEntity<?> fillCompanyInformation(@PathVariable String businessId, @PathVariable String activationCode) {
        if( notNull(activationCode) && companyService.validActivationCode(activationCode)) {
            return new ResponseEntity<>(filterApiResponse(companyService.getCompanyDataFromExternalSource(businessId, activationCode)), HttpStatus.OK);
        }
        throw new OsaamispankkiException(
                setExceptionMessage("company_activation", "Invalid activation code"));
    }

    @GetMapping("/byCompanyName/{name}")
    public ResponseEntity<?> getCompaniesByName(@PathVariable String name) {
        return new ResponseEntity(companyService.getCompaniesByName(name), HttpStatus.OK);
    }

    @GetMapping("/createdPerson")
    public ResponseEntity<?> getCompanyByPerson() {
        return new ResponseEntity(filterApiResponse(companyService.getCompanyByLoggedUser()), HttpStatus.OK);
    }

    @GetMapping("/companies")
    public ResponseEntity<?> getAllCompanies(){
        return successResponse(companyService.allCompanies());
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
