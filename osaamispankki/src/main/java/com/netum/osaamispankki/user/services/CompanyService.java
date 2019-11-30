package com.netum.osaamispankki.user.services;

import com.netum.osaamispankki.user.domain.Company;
import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.domain.UserCompany;
import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.modals.CompanyCode;
import com.netum.osaamispankki.user.modals.EmploymentType;
import com.netum.osaamispankki.user.modals.Role;
import com.netum.osaamispankki.user.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.netum.osaamispankki.user.common.UtilsMethods.*;

@Service
public class CompanyService extends HeadService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserAndCompanyService userAndCompanyService;

    public Company getCompanyDataFromExternalSource(String businessId) {
        Boolean isExist = companyRepository.existsByBusinessId(businessId);
        if (isExist) {
            return companyRepository.findByBusinessId(businessId);
        }
        Company company = null;

        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> mapOfObjects = (Map<String, Object>)
                    restTemplate.getForObject("https://avoindata.prh.fi/bis/v1/" + businessId, Object.class);
            List mapOfResult = (List<Object>) mapOfObjects.get("results");
            Map<String, Object> object = (Map<String, Object>) mapOfResult.get(0);

            String name = getDateFromRestTemplate(object, "name");
            String businessLine = getDateFromRestTemplate(object, "businessLines", "name");
            String code = getDateFromRestTemplate(object, "businessLines", "code");
            String companyForms = getDateFromRestTemplate(object, "companyForms", "name");
            Date registrationDate = stringToDateConverter("yyyy-MM-dd", getDateFromRestTemplate(object, "registrationDate"));

            User user = getUser();
            company = new Company(
                    null,
                    businessId,
                    name,
                    businessLine,
                    code,
                    companyForms,
                    user.getFirstName() + " " + user.getSurname(),
                    user.getId(),
                    registrationDate,
                    generateUUIDString(),
                    null,
                    null,
                    null,
                    null);

        } catch (Exception ex) {
            throw new OsaamispankkiException(setExceptionMessage("osaamispankki_error","Some thing goes wrong with Business fetching"));
        }
        try {
            company = companyRepository.save(company);
            userAndCompanyService.addOrSaveCompany(initAdminCompanyUser(company));
        } catch (Exception e ) {
            throw new OsaamispankkiException(setExceptionMessage("company", "Company saving error, try again"));
        }

        return company;
    }

    public List<Company> getCompaniesByName(String name)  {
        return companyRepository.findAllByCompanyNameContaining(name);
    }

    public Company getCompanyByCompanyCode(String companyName) {
        if(isBlank(companyName)) {
            return null;
        }
        return companyRepository.findByCompanyName(companyName);
    }

    public Company getCompanyByLoggedUser() {
        try {
            return companyRepository.findByCreatedBy(getUser().getId());
        } catch (Exception e) {
            throw new OsaamispankkiException(setExceptionMessage("company", "No company"));
        }

    }

    private UserCompany initAdminCompanyUser(Company company) {
        UserCompany userCompany = new UserCompany();
        userCompany.setCompany_name(company.getCompanyName());
        userCompany.setEmploymentType(EmploymentType.FULL_TIME);
        userCompany.setRole(Role.ROLE_COMPANY_ADMIN);
        userCompany.setCompany(company);
        userCompany.setAdmittedCompanyRole(true);
        userCompany.setPosition("add your position in company");
        return userCompany;
    }

    private String getDateFromRestTemplate(Map<String, Object> object, String... keys) {

        try {
            if (keys.length == 1) {
                return (String) object.get(keys[0]);
            }
            if (keys.length == 2) {
                // i = 0 in finnish, 1 in swedish and 2 in english
                int i = 0;
                return (String) ((Map<String, Object>) ((List<Object>) object.get(keys[0])).get(i)).get(keys[1]);
            }
        } catch (Exception ex) {
            throw new OsaamispankkiException(setExceptionMessage("osaamispankki_error","Some thing goes wrong with Business fetching 2"));

        }
        throw new OsaamispankkiException(setExceptionMessage("osaamispankki_error","Some thing goes wrong with Business fetching 3"));

    }

    public List<CompanyCode> allCompanies() {
        List<CompanyCode> companyCodes = new ArrayList<>();
        try {
            Iterable<Company> companies = companyRepository.findAll();
            for (Company company : companies) {
                companyCodes.add(new CompanyCode(company.getId(), company.getCompanyName()));
            }
        } catch (Exception e) {
            throw new OsaamispankkiException(setExceptionMessage("companies", "can not find companies"));
        }
        return companyCodes;
    }
}
