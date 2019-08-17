package com.netum.osaamispankki.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Education extends UserId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name Of Institution is required ")
    @Size(min = 3, message = "Name Of Institution is short")
    private String nameOfInstitution;
    @NotBlank(message = "degree is required ")
    @Size(min = 3, message = "degree is short")
    private String degree;
    @NotBlank(message = "location is required ")
    @Size(min = 3, message = "location is short")
    private String location;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date startYear;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date endYear;

}
