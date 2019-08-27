package com.netum.osaamispankki.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Experience extends UserId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "company is required")
    private String company;

    @NotBlank(message = "position is required")
    private String position;

    @NotBlank(message = "description is required")
    private String description;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date startYear;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date endYear;
}
