package uz.java.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.java.service.FileService;
import uz.java.service.MinioService;


@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;
    private final MinioService minioService;

    public FileController(FileService fileService, MinioService minioService) {
        this.fileService = fileService;
        this.minioService = minioService;
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(fileService.uploadFile(file));
    }

    @PostMapping(path = "/upload/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(minioService.saveAvatar(file, file.getOriginalFilename()));
    }

    @PostMapping(path = "/upload/company-logo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCompanyLogo(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(minioService.saveCompanyLogo(file, file.getOriginalFilename()));
    }

    @PostMapping(path = "/upload/company-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCompanyImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(minioService.saveCompanyImage(file, file.getOriginalFilename()));
    }

    @PostMapping(path = "/upload/resume-photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadResumePhoto(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(minioService.saveResumePhoto(file, file.getOriginalFilename()));
    }

    @PostMapping(path = "/upload/portfolio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPortfolioFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(minioService.savePortfolioFile(file, file.getOriginalFilename()));
    }

    @PostMapping(path = "/upload/certificate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCertificateFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(minioService.saveCertificateFile(file, file.getOriginalFilename()));
    }

    @GetMapping("/presigned-url")
    public ResponseEntity<String> getPresignedUrl(@RequestParam String objectName) {
        return ResponseEntity.ok(minioService.generatePresignedUrl(objectName));
    }
}
