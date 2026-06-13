package uz.java.service;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.java.exception.FileStorageException;

import java.util.Objects;

@Service
@Slf4j
public class FileService {

    private final MinioService minioService;

    public FileService(MinioService minioService) {
        this.minioService = minioService;
    }

    public String uploadFile(MultipartFile file) {
        if (file.isEmpty())
            throw new FileStorageException("file.invalid.path");

        String originalFilename = StringUtils.cleanPath(
                Objects.requireNonNull(file.getOriginalFilename())
        );

        if (originalFilename.contains(".."))
            throw new ValidationException("Failed to store file with relative path");

        String contentType = Objects.requireNonNull(file.getContentType());
        String path;
        if (contentType.startsWith("image") && !contentType.contains("svg+xml")) {
            MultipartFile fileToUpload = file;
            path = minioService.saveFile(fileToUpload, originalFilename);
        } else {
            path = minioService.saveFile(file, originalFilename);
        }
        return path;
    }
}
