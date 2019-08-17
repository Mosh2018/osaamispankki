package com.netum.osaamispankki.user.services;

import com.netum.osaamispankki.user.domain.PhotoFile;
import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.repository.PhotoFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import static com.netum.osaamispankki.user.common.UtilsMethods.setExceptionMessage;

@Service
public class StorePhotoService {

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

            // check if file type not jpg throw exceptions
            PhotoFile photo = new PhotoFile(filename,
                    multipartFile.getContentType(),
                    multipartFile.getBytes());
        return fileRepository.save(photo);
        }  catch (Exception e) {
            throw new OsaamispankkiException(
                    setExceptionMessage("photo",
                            "Could not store file " + filename + ". Please try again!-->"
                                    + e.getMessage()));
        }
    }

    public PhotoFile getPhoto (final String id) {
        return fileRepository.findById(id).orElseThrow(() -> new OsaamispankkiException(
                setExceptionMessage("photo", "photo not found with id "+ id)));

    }
}
