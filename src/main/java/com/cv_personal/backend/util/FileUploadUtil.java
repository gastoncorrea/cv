package com.cv_personal.backend.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

    public static String saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String uniqueFileName = generateUniqueFileName(fileName);
        Path filePath = uploadPath.resolve(uniqueFileName);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        return "/uploads/" + uniqueFileName; // Return relative path for storage
    }

    public static boolean deleteFile(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            return false;
        }
        // Assuming filePath stored in DB is relative to the 'uploads' directory
        Path fileToDelete = Paths.get("uploads").resolve(filePath.replace("/uploads/", ""));
        
        if (Files.exists(fileToDelete)) {
            Files.delete(fileToDelete);
            return true;
        }
        return false;
    }

    public static String generateUniqueFileName(String originalFileName) {
        String extension = "";
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < originalFileName.length() - 1) {
            extension = originalFileName.substring(dotIndex);
        }
        return UUID.randomUUID().toString() + extension;
    }
}
