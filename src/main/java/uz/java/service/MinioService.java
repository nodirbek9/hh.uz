package uz.java.service;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import uz.java.util.MD5Decode;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.leftPad;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinioService {

    @Value("${root.path}")
    private String rootPath;


    private static final String defaultUploadPath = "{YYYY}/{MM}/{DD}/";

    @Value("${project.upload.path.default-bucket}")
    private String defaultBucketName;

    private final MinioClient minioClient;

    public String saveFile(MultipartFile file, String originalFilename) {
        String res;
//        FileEntity fileEntity = new FileEntity();
//        fileEntity.setName(StringUtils.stripFilenameExtension(Objects.requireNonNull(originalFilename)));
//        fileEntity.setSize(file.getSize());
//        fileEntity.setContentType(file.getContentType());
//        fileEntityRepository.save(fileEntity);
        String uniqueKey = MD5Decode.md5Decode(UUID.randomUUID());
        uniqueKey = String.format("%s.%s", uniqueKey, StringUtils.getFilenameExtension(originalFilename));
        StringBuilder wholePath = new StringBuilder(rootPath);
        String realWholePath = prepareUploadPath(wholePath.append(defaultUploadPath).toString());
        char lastChar = realWholePath.charAt(realWholePath.length() - 1);
        String objectName;
        if (lastChar == '/')
            objectName = String.format("%s%s", realWholePath, uniqueKey);
        else
            objectName = String.format("%s/%s", realWholePath, uniqueKey);
        try (InputStream inputStream = file.getInputStream()) {
            res = uploadFile(defaultBucketName, objectName, inputStream);
        } catch (IOException | MinioException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File can not upload", e);
        }
//        fileEntity.setObjectName(res);
//        fileEntity.setPath(res);
//        fileEntityRepository.save(fileEntity);
        return res;
    }

    public void createBucket(String bucketName) throws MinioException {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new MinioException("Error creating bucket: " + bucketName + "; " + e.getMessage());
        }
    }

    public String uploadFile(String bucketName, String objectName, InputStream inputStream) throws MinioException {
        try {
            log.info("Bucket Name: {}, Object Name: {}", bucketName, objectName);
            createBucket(bucketName);
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(inputStream, inputStream.available(), -1)
                    .build());
            StatObjectArgs statObject = StatObjectArgs.builder().bucket(bucketName).object(objectName).build();
            minioClient.statObject(statObject).etag();
            return objectName;
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new MinioException("Unexpected error happened: " + e.getMessage());
        }
    }

    public String prepareUploadPath(String uploadPath) {
        if (!StringUtils.hasText(uploadPath))
            uploadPath = defaultUploadPath;

        Calendar now = Calendar.getInstance();
        return uploadPath
                .replace("{YYYY}", String.valueOf(now.get(Calendar.YEAR)))
                .replace("{MM}", leftPad(String.valueOf(now.get(Calendar.MONTH) + 1), 2, "0"))
                .replace("{DD}", leftPad(String.valueOf(now.get(Calendar.DAY_OF_MONTH)), 2, "0"));
    }

    public String generatePresignedUrl(String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(defaultBucketName)
                            .object(objectName)
                            .expiry(7, TimeUnit.HOURS)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Presigned URL yaratib bo'lmadi", e);
        }
    }

    public String saveAvatar(MultipartFile file, String originalFilename) {
        validateImageFile(file);
        String uniqueKey = MD5Decode.md5Decode(UUID.randomUUID());
        uniqueKey = String.format("%s.%s", uniqueKey, StringUtils.getFilenameExtension(originalFilename));
        StringBuilder wholePath = new StringBuilder(rootPath);
        wholePath.append("avatars/");
        String realWholePath = prepareUploadPath(wholePath.append(defaultUploadPath).toString());
        String objectName = String.format("%s%s", realWholePath, uniqueKey);

        try (InputStream inputStream = file.getInputStream()) {
            return uploadFile(defaultBucketName, objectName, inputStream);
        } catch (IOException | MinioException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Avatar upload failed", e);
        }
    }

    public String saveCompanyLogo(MultipartFile file, String originalFilename) {
        validateImageFile(file);
        String uniqueKey = MD5Decode.md5Decode(UUID.randomUUID());
        uniqueKey = String.format("%s.%s", uniqueKey, StringUtils.getFilenameExtension(originalFilename));
        StringBuilder wholePath = new StringBuilder(rootPath);
        wholePath.append("companies/logos/");
        String realWholePath = prepareUploadPath(wholePath.append(defaultUploadPath).toString());
        String objectName = String.format("%s%s", realWholePath, uniqueKey);

        try (InputStream inputStream = file.getInputStream()) {
            return uploadFile(defaultBucketName, objectName, inputStream);
        } catch (IOException | MinioException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Company logo upload failed", e);
        }
    }

    public String saveCompanyImage(MultipartFile file, String originalFilename) {
        validateImageFile(file);
        String uniqueKey = MD5Decode.md5Decode(UUID.randomUUID());
        uniqueKey = String.format("%s.%s", uniqueKey, StringUtils.getFilenameExtension(originalFilename));
        StringBuilder wholePath = new StringBuilder(rootPath);
        wholePath.append("companies/images/");
        String realWholePath = prepareUploadPath(wholePath.append(defaultUploadPath).toString());
        String objectName = String.format("%s%s", realWholePath, uniqueKey);

        try (InputStream inputStream = file.getInputStream()) {
            return uploadFile(defaultBucketName, objectName, inputStream);
        } catch (IOException | MinioException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Company image upload failed", e);
        }
    }

    public String saveResumePhoto(MultipartFile file, String originalFilename) {
        validateImageFile(file);
        String uniqueKey = MD5Decode.md5Decode(UUID.randomUUID());
        uniqueKey = String.format("%s.%s", uniqueKey, StringUtils.getFilenameExtension(originalFilename));
        StringBuilder wholePath = new StringBuilder(rootPath);
        wholePath.append("resumes/photos/");
        String realWholePath = prepareUploadPath(wholePath.append(defaultUploadPath).toString());
        String objectName = String.format("%s%s", realWholePath, uniqueKey);

        try (InputStream inputStream = file.getInputStream()) {
            return uploadFile(defaultBucketName, objectName, inputStream);
        } catch (IOException | MinioException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Resume photo upload failed", e);
        }
    }

    public String savePortfolioFile(MultipartFile file, String originalFilename) {
        String uniqueKey = MD5Decode.md5Decode(UUID.randomUUID());
        uniqueKey = String.format("%s.%s", uniqueKey, StringUtils.getFilenameExtension(originalFilename));
        StringBuilder wholePath = new StringBuilder(rootPath);
        wholePath.append("portfolios/");
        String realWholePath = prepareUploadPath(wholePath.append(defaultUploadPath).toString());
        String objectName = String.format("%s%s", realWholePath, uniqueKey);

        try (InputStream inputStream = file.getInputStream()) {
            return uploadFile(defaultBucketName, objectName, inputStream);
        } catch (IOException | MinioException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Portfolio file upload failed", e);
        }
    }

    public String saveCertificateFile(MultipartFile file, String originalFilename) {
        String uniqueKey = MD5Decode.md5Decode(UUID.randomUUID());
        uniqueKey = String.format("%s.%s", uniqueKey, StringUtils.getFilenameExtension(originalFilename));
        StringBuilder wholePath = new StringBuilder(rootPath);
        wholePath.append("certificates/");
        String realWholePath = prepareUploadPath(wholePath.append(defaultUploadPath).toString());
        String objectName = String.format("%s%s", realWholePath, uniqueKey);

        try (InputStream inputStream = file.getInputStream()) {
            return uploadFile(defaultBucketName, objectName, inputStream);
        } catch (IOException | MinioException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Certificate file upload failed", e);
        }
    }

    private void validateImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File must be an image");
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image size must not exceed 10MB");
        }
    }
}
