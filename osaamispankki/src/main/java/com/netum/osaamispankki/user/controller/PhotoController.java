package com.netum.osaamispankki.user.controller;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
            String photoUrl = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/downloadPhoto/")
                    .path(photoFile.getId())
                    .toUriString();

            return new ResponseEntity<>(photoUrl, HttpStatus.OK);
        } catch (Exception e)  {


            throw new Exception(e.getMessage());
        }

    }

    @GetMapping("/download_photo/{fileId}")
    public ResponseEntity<?> downloadPhoto(@PathVariable String fileId)  {

        PhotoFile   photoFile = photoService.getPhoto(fileId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(photoFile.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + photoFile.getFilename() + "\"")
                .body(new ByteArrayResource(photoFile.getPhoto()));
    }

}
