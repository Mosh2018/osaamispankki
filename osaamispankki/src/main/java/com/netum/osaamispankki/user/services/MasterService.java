package com.netum.osaamispankki.user.services;

import com.netum.osaamispankki.user.domain.ActivationCode;
import com.netum.osaamispankki.user.repository.ActivationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MasterService extends HeadService {
    @Autowired
    private ActivationCodeRepository activationCodeRepository;

    public String generateAnActivationCode() {
        String activationCode = UUID.randomUUID().toString();
        ActivationCode activationCodeEntity = new ActivationCode();
        activationCodeEntity.setActivationCode(activationCode);
        this.activationCodeRepository.save(activationCodeEntity);
        return activationCode;
    }

    public List<ActivationCode> getAllActivationCodes() {
        Iterable<ActivationCode> activationCodes = this.activationCodeRepository.findAll();
        return activationCodesAsList(activationCodes);
    }

    public List<ActivationCode> getUsedCodes() {
        Iterable<ActivationCode> activationCodes = this.activationCodeRepository.findAllByUsed(true);
        return activationCodesAsList(activationCodes);
    }

    public List<ActivationCode> getNotUsedCodes() {
        Iterable<ActivationCode> activationCodes = this.activationCodeRepository.findAllByUsed(false);
        return activationCodesAsList(activationCodes);
    }


    public List<ActivationCode> activationCodesAsList(Iterable<ActivationCode> activationCodes) {
        List<ActivationCode> activationCodeList = new ArrayList<>();
        while (activationCodes.iterator().hasNext()) {
            activationCodeList.add(activationCodes.iterator().next());
        }
        return activationCodeList;
    }
}
