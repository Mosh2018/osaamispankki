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

    public ActivationCode generateAnActivationCode() {
        ActivationCode activationCodeEntity = new ActivationCode();
        activationCodeEntity.setActivationCode(UUID.randomUUID().toString());
        activationCodeEntity = this.activationCodeRepository.save(activationCodeEntity);
        return activationCodeEntity;
    }

    public List<ActivationCode> getAllActivationCodes() {
        return this.activationCodeRepository.findAll();
    }

    public List<ActivationCode> getUsedCodes() {
        return this.activationCodeRepository.findAllByUsed(true);
    }

    public List<ActivationCode> getNotUsedCodes() {
        return this.activationCodeRepository.findAllByUsed(false);
    }
}
