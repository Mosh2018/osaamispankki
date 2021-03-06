package com.netum.osaamispankki.user.controller.unclassified;

import com.netum.osaamispankki.user.common.GenericHelper;
import com.netum.osaamispankki.user.domain.PhotoFile;
import com.netum.osaamispankki.user.services.StorePhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/photo")
public class PhotoController {
    @Autowired
    private StorePhotoService photoService;

    @PostMapping("/upload_photo")
    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile photo) throws Exception{

        try {
            PhotoFile photoFile =   photoService.storeFile(photo);
            return imageResponse(photoFile);
        } catch (Exception e)  {
           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/download_photo")
    public ResponseEntity<?> downloadPhoto() throws IOException {

        PhotoFile photoFile = photoService.getPhoto();
        if (GenericHelper.notNull(photoFile)) {
            return imageResponse(photoFile);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }


    }

    @DeleteMapping("/personal-photo")
    public ResponseEntity<?> deletePhoto() {
        return new ResponseEntity<>(this.photoService.deletePhoto(), HttpStatus.OK);
    }

    private ResponseEntity<ByteArrayResource> imageResponse(PhotoFile photoFile) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(photoFile.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + photoFile.getFilename() + "\"")
                .body(new ByteArrayResource(photoFile.getPhoto()));
    }

}
