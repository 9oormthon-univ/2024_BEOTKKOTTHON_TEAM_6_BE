package org.goormthon.beotkkotthon.rebook.utility;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.constant.Constants;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ImageUtil {
    private final AmazonS3 s3Client;

    @Value("${spring.cloud.aws.s3.credentials.bucket}")
    private String BUCKET_PATH;

    @Value("${spring.cloud.aws.s3.url}")
    private String BUCKET_URL;


    /**
     * 이미지 업로드
     * 이미지 파일을 S3에 업로드하고, 업로드된 이미지의 이름을 반환한다.
     * @param file
     * @return 이미지 이름
     */
    public String uploadImage(MultipartFile file) {
        final String contentType = file.getContentType();

        assert contentType != null;
        if (!contentType.startsWith(Constants.IMAGE_CONTENT_PREFIX)) {
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }

        final String uuidImageName = UUID.randomUUID() + "." + contentType.split("/")[1];
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try (final InputStream inputStream = file.getInputStream()) {
            String keyName = String.format("%s/%s", Constants.DIRECTORY_PATH, uuidImageName);

            s3Client.putObject(
                    new PutObjectRequest(BUCKET_PATH, keyName, inputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            throw new CommonException(ErrorCode.EXTERNAL_SERVER_ERROR);
        }

        return uuidImageName;
    }

    /**
     * 이미지 URL 반환
     * 이미지 이름을 받아 이미지 URL을 반환한다.
     * @param key
     * @return 이미지 URL
     */
    public String getImageUrl(String key) {
        return String.format("%s/%s/%s", BUCKET_URL, Constants.DIRECTORY_PATH, key);
    }
}
