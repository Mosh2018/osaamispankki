package com.netum.osaamispankki.user.services;

import com.netum.osaamispankki.user.domain.PhotoFile;
import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.modals.CResponse;
import com.netum.osaamispankki.user.repository.PhotoFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import static com.netum.osaamispankki.user.common.GenericHelper.notNull;
import static com.netum.osaamispankki.user.common.UtilsMethods.setExceptionMessage;

@Service
public class StorePhotoService extends HeadService{

    @Autowired
    private PhotoFileRepository fileRepository;

    public PhotoFile storeFile(MultipartFile multipartFile) {

        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        try {
            if (filename.contains("..")) {
                throw new OsaamispankkiException(
                        setExceptionMessage("photo",
                                "Sorry! photo's name contains invalid path sequence "));
            }

            PhotoFile photoFile = this.fileRepository.findByUserId(getUser().getId());
            PhotoFile photo;
            if (notNull(photoFile)) {
                photo = new PhotoFile(
                        photoFile.getId(),
                        filename,
                        multipartFile.getContentType(),
                        getUser().getId(),
                        multipartFile.getBytes());
            } else {

                // check if file type not jpg throw exceptions
               photo = new PhotoFile(filename,
                        multipartFile.getContentType(),
                        getUser().getId(),
                        multipartFile.getBytes());
        }

        return fileRepository.save(photo);
        }  catch (Exception e) {
            throw new OsaamispankkiException(
                    setExceptionMessage("photo",
                            "Could not store file " + filename + ". Please try again!-->"
                                    + e.getMessage()));
        }
    }

    public PhotoFile getPhoto () {
        try {
            return fileRepository.findByUserId(getUser().getId());
        } catch (Exception e) {
           throw new OsaamispankkiException(
                    setExceptionMessage("photo", "photo not found for this user: " + getUser().getFirstName()));
        }
    }

    public CResponse deletePhoto() {
       if (this.fileRepository.existsByUserId(getUser().getId())) {
           try {
               this.fileRepository.deleteByUserId(getUser().getId());
               return new CResponse("delete-operation", true);
           } catch (Exception e) {
               throw new OsaamispankkiException(
                       setExceptionMessage("photo", "photo not found for this user: " + getUser().getFirstName()));
           }
       }
        return new CResponse("delete-operation", false);
    }
}
