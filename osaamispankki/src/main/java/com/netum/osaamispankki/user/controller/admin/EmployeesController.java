package com.netum.osaamispankki.user.controller.admin;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("endpoint/company")
public class EmployeesController {

    @GetMapping("/employees")
    public ResponseEntity<?> getCompanyEmployees() {
        return new ResponseEntity<>("I see all this company employees", HttpStatus.OK);
    }
}
