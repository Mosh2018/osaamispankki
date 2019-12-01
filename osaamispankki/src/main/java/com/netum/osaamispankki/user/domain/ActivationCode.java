package com.netum.osaamispankki.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long company_id;
    private String company_name;
    private Boolean used = false;

    @NotBlank
    private String activationCode;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date created_At;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date update_At;

    @PrePersist
    public void onCreated() {
        this.created_At = new Date();
    }

    @PreUpdate
    public void onUpdated() {
        this.update_At = new Date();
    }
}
