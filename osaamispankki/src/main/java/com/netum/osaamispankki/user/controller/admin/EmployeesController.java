package com.netum.osaamispankki.user.controller.admin;


import com.netum.osaamispankki.user.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("endpoint/company")
public class EmployeesController {

    @Autowired
    private EmployeesService employeesService;

    @GetMapping("/employees")
    public ResponseEntity<?> getCompanyEmployees() {
        return new ResponseEntity<>("I see all this company employees", HttpStatus.OK);
    }

    @GetMapping("/confirm_employee")
    public ResponseEntity<?> confirmEmployeeToCompany(@Valid @RequestBody String userId) {
        return new ResponseEntity<>(employeesService.confirmUserToCompany(Long.parseLong(userId)), HttpStatus.OK);
    }
}
