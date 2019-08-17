package com.netum.osaamispankki.user.services;

import com.netum.osaamispankki.user.domain.Education;
import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.netum.osaamispankki.user.common.GenericHelper.notNull;
import static com.netum.osaamispankki.user.common.UtilsMethods.throwException;

@Service
public class CVService extends HeadService {
   @Autowired
   private EducationRepository educationRepository;

    public Iterable<Education> save(Education[] educations) {
        User user = getUser();
        if(notNull(user)) {
            List<Education> educationArrayList = new ArrayList<>();

            for (Education education: educations) {
                education.setUserId(user.getId());
                educationArrayList.add(education);
                this.educationRepository.save(education);
            }
            return educationArrayList;
        }
        throw throwException("education", "education not saved");
    }
    // save education
    // get education list
    // get newest education

}
