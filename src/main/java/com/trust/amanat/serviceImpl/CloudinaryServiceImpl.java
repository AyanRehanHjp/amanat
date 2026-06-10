package com.trust.amanat.serviceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file, String folderName) {

        try {

            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folderName,
                            "resource_type", "image",
                            "use_filename", true,
                            "unique_filename", true
                    )
            );

            return uploadResult.get("secure_url").toString();

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException("File upload failed");
        }
    }

    @Override
    public void deleteFile(String imageUrl) {

        try {

            String publicId = imageUrl
                    .substring(imageUrl.indexOf(AppConstants.Message.PROFILE_PIC+"/"))
                    .replace(".jpg", "")
                    .replace(".png", "")
                    .replace(".jpeg", "")
                    .replace(".pdf", "");

            cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.asMap(
                            "resource_type", "image"
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException("File delete failed");
        }
    }
}