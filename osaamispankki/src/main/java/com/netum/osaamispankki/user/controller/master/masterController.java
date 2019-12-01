package com.netum.osaamispankki.user.controller.master;

import com.netum.osaamispankki.user.services.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasAuthority('ROLE_MASTER')")
@RequestMapping("/api/master-admin-master")
public class masterController {

    @Autowired
    private MasterService masterService;

    @GetMapping("/generate")
    public ResponseEntity<?> generateActivationCode() {
        return ResponseEntity.ok(this.masterService.generateAnActivationCode());
    }

    @GetMapping("/allActivatedCodes")
    public ResponseEntity<?> getAllCodes() {
        return ResponseEntity.ok(this.masterService.getAllActivationCodes());
    }

    @GetMapping("/usedActivatedCodes")
    public ResponseEntity<?> getUsedCodes() {
        return ResponseEntity.ok(this.masterService.getUsedCodes());
    }


    @GetMapping("/notUsedActivatedCodes")
    public ResponseEntity<?> getNotUsedCodes() {
        return ResponseEntity.ok(this.masterService.getNotUsedCodes());
    }

}
