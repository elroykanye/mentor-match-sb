package com.kanyelings.telmah.mentormatchsb.business.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;


public class ImageUtil {
    @Value("${image.upload-path}")
    private static String imageUploadDir;

    public static String saveUserImage(MultipartFile file, String username) throws IOException {
        String fileName = username.concat(
                StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()))
        );

        FileUploadUtil.saveFile(imageUploadDir, fileName, file);

        return fileName;
    }
}
