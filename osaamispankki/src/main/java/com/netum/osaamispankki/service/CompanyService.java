package com.netum.osaamispankki.service;

import com.netum.osaamispankki.domain.Company;
import com.netum.osaamispankki.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company getCompanyDataFromExternalSource(String businessId) {
        Company company;
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

            company = new Company(null, businessId, name, businessLine, code, companyForms);
            companyRepository.save(company);

        } catch (Exception ex) {
            throw new OsaamispankkiException("Some thing goes wrong with Business fetching");
        }
        return company;
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
            throw new OsaamispankkiException("Some thing goes wrong with Business fetching 2");
        }
        throw new OsaamispankkiException("Some thing goes wrong with Business fetching 3");
    }
}
