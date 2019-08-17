package com.netum.osaamispankki.user.services;

import com.netum.osaamispankki.user.domain.HomeAddress;
import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.modals.UserProfile;
import com.netum.osaamispankki.user.repository.HomeAddressRepository;
import com.netum.osaamispankki.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.netum.osaamispankki.user.common.GenericHelper.isNull;

@Service
public class BasicInformationService extends HeadService{

    @Autowired private HomeAddressRepository homeAddressRepository;
    @Autowired private UserRepository userRepository;

    public UserProfile saveHomeAddress(UserProfile userProfile) {
        User user = getUser();
        user.setFirstName(userProfile.getFirstName());
        user.setSurname(userProfile.getSurname());
        user.setPhoneNo(userProfile.getPhoneNo());
        user.setBirthday(userProfile.getBirthday());
        HomeAddress homeAddress = user.getHomeAddress();
        if (isNull(homeAddress)) {
           homeAddress = new HomeAddress(userProfile.getAddress(), user);
        } else {
            homeAddress.setStreet(userProfile.getAddress().getStreet());
            homeAddress.setPostcode(userProfile.getAddress().getPostcode());
            homeAddress.setCity(userProfile.getAddress().getCity());
            homeAddress.setCountry(userProfile.getAddress().getCountry());
        }
        user.setHomeAddress(homeAddress);
        homeAddressRepository.save(homeAddress);
        userRepository.save(user);
        return userProfile;
    }

    public UserProfile getPersonalInformation() {
        User user = getUser();
         return new UserProfile(user);
    }
}
