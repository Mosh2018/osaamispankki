package com.netum.osaamispankki.user.service;

import com.netum.osaamispankki.user.domain.Company;
import com.netum.osaamispankki.user.domain.CompanyConformation;
import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.modals.CompanyUser;
import com.netum.osaamispankki.user.repository.CompanyConformationRepository;
import com.netum.osaamispankki.user.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.netum.osaamispankki.user.common.UtilsMethods.isBlank;
import static com.netum.osaamispankki.user.common.UtilsMethods.setExceptionMessage;
import static com.netum.osaamispankki.user.exceptions.ExceptionsMessage.COMPANY_ID_NOT_FOUND;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyConformationRepository companyConformationRepository;

    @Autowired
    private UserService userService;

    public Company getCompanyDataFromExternalSource(String businessId) {
        Boolean isExist = companyRepository.existsByBusinessId(businessId);
        if (isExist) {
            return companyRepository.findByBusinessId(businessId);
        }
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

            User user = userService.getUser();
            Company company = new Company(null, businessId, name, businessLine, code, companyForms, user.getUsername(), null, null);
            try {
                company = companyRepository.save(company);
            } catch (Exception e ) {
                throw new OsaamispankkiException(setExceptionMessage("company", "Company saving error, try again"));
            }

            try {
                CompanyConformation companyConformation = new CompanyConformation();
                companyConformation.setCompanyId(company.getId());
                companyConformationRepository.save(companyConformation);
            } catch (Exception e) {
                throw new OsaamispankkiException(setExceptionMessage("company", "Company has not been registered, " +
                        "connect to admin to resolve the issue"));
            }
            return company;

        } catch (Exception ex) {
            throw new OsaamispankkiException(setExceptionMessage("osaamispankki_error","Some thing goes wrong with Business fetching"));
        }
    }

    public Set<CompanyUser> getWorkersOfCompany(Long id) {
        CompanyConformation companyConformation = companyConformationRepository.findByCompanyId(id);
        if (companyConformation != null) {
            return companyConformationRepository.findByCompanyId(id).getCompanyUsers().stream().map( x -> {
                CompanyUser companyUser = new CompanyUser(x.getId(), x.getUsername(),true);
                return companyUser;
            }).collect(Collectors.toSet());
        }
        throw new OsaamispankkiException(setExceptionMessage(COMPANY_ID_NOT_FOUND.getField(), COMPANY_ID_NOT_FOUND.getMessage()));
    }

    public List<Company> getCompaniesByName(String name)  {
        return companyRepository.findAllByCompanyNameContaining(name);
    }

    public Company getCompany(String companyName) {
        if(isBlank(companyName)) {
            return null;
        }
        return companyRepository.findByCompanyName(companyName);
    }

    public User saveNewCompany(String companyName) {
        return userService.addCompanyToUser(companyName);
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


}
