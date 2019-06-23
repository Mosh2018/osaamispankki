package com.netum.osaamispankki.service;

import com.netum.osaamispankki.domain.Profile;
import com.netum.osaamispankki.domain.User;
import com.netum.osaamispankki.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.exceptions.UniqueException;
import com.netum.osaamispankki.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.netum.osaamispankki.common.UtilsMethods.setExceptionMessage;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserService userService;

    public Profile createProfile(Profile newProfile, String username) {
        boolean isExisting = profileRepository.existsByEmail(newProfile.getEmail());
        if (isExisting) {
            throw new UniqueException(setExceptionMessage("email", "email has been used"));
        }
        isExisting = profileRepository.existsByPhoneNo(newProfile.getPhoneNo());
        if (isExisting) {
            throw new UniqueException(setExceptionMessage("phoneNo", "input another phone number"));
        }
        try {
            User user = userService.getUser(username);
            user.setProfile(newProfile);
            newProfile.setUser(user);
            return this.userService.updateUser(user).getProfile();
        } catch (Exception ex) {
           throw new OsaamispankkiException(Profile.class.toString() + " has unexpected error");
        }

    }

    public Object getProfileByUsename(String username) {
        User user = userService.getUser(username);
        return profileRepository.findById(user.getId());
    }

    public Object updateProfile(Profile updatedProfile, String username) {
        User user = userService.getUser(username);
        Profile profile = user.getProfile();
        updatedProfile.setId(profile.getId());
        return  profileRepository.save(updatedProfile);

    }
}
