package com.netum.osaamispankki.user.services;

import com.netum.osaamispankki.user.domain.Education;
import com.netum.osaamispankki.user.domain.Experience;
import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.repository.EducationRepository;
import com.netum.osaamispankki.user.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.netum.osaamispankki.user.common.GenericHelper.notNull;
import static com.netum.osaamispankki.user.common.UtilsMethods.throwException;

@Service
public class CVService extends HeadService {

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    public Education save(Education education) {
        User user = getUser();
        if (notNull(user)) {
            education.setUserId(user.getId());
            return this.educationRepository.save(education);
        }
        throw throwException("education", "education not saved");
    }

    public Optional<List<Education>> getEducations() {
        return this.educationRepository.findAllByUserId(getUser().getId());
    }

    public void deleteEducation(Long id) {
        this.educationRepository.deleteById(id);
    }

    public Experience save(Experience experience) {
        User user = getUser();
        if (notNull(user)) {
            experience.setUserId(user.getId());
            return this.experienceRepository.save(experience);
        }
        throw throwException("experience", "experience not saved");
    }

    public Optional<List<Experience>> getExperiences() {
        return this.experienceRepository.findAllByUserId(getUser().getId());
    }

    public void deleteExperience(Long id) {
        this.experienceRepository.deleteById(id);
    }

}
