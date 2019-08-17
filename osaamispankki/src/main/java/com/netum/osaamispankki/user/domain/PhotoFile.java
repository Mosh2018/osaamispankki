package com.netum.osaamispankki.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhotoFile {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String filename;
    private String type;

    @Lob
    @Column(name = "photo", columnDefinition="BLOB")
    private byte[] photo;

    public PhotoFile(String filename, String type, byte[] photo) {
        this.filename = filename;
        this.type = type;
        this.photo = photo;
    }
}
