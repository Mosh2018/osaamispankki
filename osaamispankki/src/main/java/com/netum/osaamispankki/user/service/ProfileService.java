package com.netum.osaamispankki.user.service;

import com.netum.osaamispankki.user.domain.Profile;
import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.exceptions.UniqueException;
import com.netum.osaamispankki.user.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.netum.osaamispankki.user.common.UtilsMethods._false;
import static com.netum.osaamispankki.user.common.UtilsMethods.setExceptionMessage;
import static com.netum.osaamispankki.user.exceptions.ExceptionsMessage.PROFILE_NOT_FOUND;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserService userService;

    public Profile createProfile(Profile newProfile, String username) {
        boolean userExist = userService.isExist(username);
        if (_false(userExist)) {
            throw new OsaamispankkiException(setExceptionMessage("username", "username not found"));
        }
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
            return this.userService.save(user).getProfile();
        } catch (Exception ex) {
           throw new OsaamispankkiException(Profile.class.toString() + " has unexpected error");
        }

    }

    public Profile getProfileByUsername(String username) {
        User user = userService.getUser(username);
        return profileRepository.findById(user.getId()).get();
    }

    public Profile updateProfile(Profile updatedProfile, String username) {
        User user = userService.getUser(username);
        if (user.getProfile() == null) {
            throw new OsaamispankkiException(setExceptionMessage(PROFILE_NOT_FOUND.getField(), PROFILE_NOT_FOUND.getMessage()));
        }
        Profile profile = user.getProfile();
        updatedProfile.setId(profile.getId());
        updatedProfile.setUser(user);
        return  profileRepository.save(updatedProfile);

    }
}
