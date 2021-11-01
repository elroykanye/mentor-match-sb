package com.kanyelings.telmah.mentormatchsb.business.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    public static void saveFile(String imageUploadDir, String fileName, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(imageUploadDir);

        if(!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

        try (InputStream inputStream = file.getInputStream()) {
            Path imageFilePath = uploadPath.resolve(fileName);

            Files.copy(inputStream, imageFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            throw new IOException("Could not save image file: "+ fileName, e);
        }
    }
}
