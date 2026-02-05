package com.cv_personal.backend.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadUtil {

    /**
     * Saves a multipart file to the specified upload directory with a unique generated filename.
     *
     * @param uploadDir The directory where the file will be saved.
     * @param multipartFile The file to be saved.
     * @return The generated unique filename (e.g., "unique-id.jpg").
     * @throws IOException If an I/O error occurs.
     */
    public String saveFile(String uploadDir, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String uniqueFileName = generateUniqueFileName(originalFileName);
        Path filePath = uploadPath.resolve(uniqueFileName);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        return uniqueFileName; // Return only the unique filename
    }

    /**
     * Deletes a file from the specified upload directory.
     *
     * @param uploadDir The base upload directory.
     * @param fileName The name of the file to delete (e.g., "unique-id.jpg").
     * @return true if the file was successfully deleted, false otherwise.
     * @throws IOException If an I/O error occurs.
     */
    public boolean deleteFile(String uploadDir, String fileName) throws IOException {
        if (fileName == null || fileName.isEmpty()) {
            return false;
        }
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        
        if (Files.exists(filePath)) {
            Files.delete(filePath);
            return true;
        }
        return false;
    }

    /**
     * Generates a unique filename using UUID, preserving the original file extension.
     *
     * @param originalFileName The original name of the file.
     * @return A unique filename.
     */
    private String generateUniqueFileName(String originalFileName) {
        String extension = "";
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < originalFileName.length() - 1) {
            extension = originalFileName.substring(dotIndex);
        }
        return UUID.randomUUID().toString() + extension;
    }
}
