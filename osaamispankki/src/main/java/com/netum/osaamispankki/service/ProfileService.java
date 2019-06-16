package com.netum.osaamispankki.service;

import com.netum.osaamispankki.domain.Profile;
import com.netum.osaamispankki.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.exceptions.UniqueException;
import com.netum.osaamispankki.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Profile createOrUpdateProfile(Profile newProfile) {
        boolean isExisting = profileRepository.existsByEmail(newProfile.getEmail());
        if (isExisting) {
            throw new UniqueException(setExceptionMessage("email", "email has been used"));
        }
        isExisting = profileRepository.existsByPhoneNo(newProfile.getPhoneNo());
        if (isExisting) {
            throw new UniqueException(setExceptionMessage("phoneNo", "input another phone number"));
        }
        try {
            return this.profileRepository.save(newProfile);
        } catch (Exception ex) {
           throw new OsaamispankkiException(Profile.class.toString() + " has unexpected error");
        }

    }
    private String setExceptionMessage(String field, String message) {
        return field + ": " + message;
    }
}
