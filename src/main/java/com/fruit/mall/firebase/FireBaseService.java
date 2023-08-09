package com.fruit.mall.firebase;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class FireBaseService {

    @Value("${firebase.bucket}")
    private String firebaseBucket;

    public String uploadFiles(MultipartFile file, String path, String fileName) throws IOException {

        // 업로드 파일 유효성 검사
        if (file.isEmpty()) {
            log.error("File is Empty");
            return "File is Empty";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(path);
        sb.append("/");
        sb.append(fileName);

        // Firebase Storage의 버킷을 가져온다.
        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);

        // MultipartFile을 바이트 배열로 변환하여 해당 파일의 내용을 가져온다.
        InputStream content = new ByteArrayInputStream(file.getBytes());

        // Firebase 스토리지에 파일 업로드. (업로드될 파일의 경로와 파일 이름, 파일의 내용, 파일 타입 )
        Blob blob = bucket.create(sb.toString(), content, file.getContentType());

        log.info("이미지 저장 경로 {}", sb.toString());

        // 업로드된 파일의 다운로드 URL을 반환
        return blob.getMediaLink();
    }


    public void deleteImage(String imageUrl) {
        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);

        // 이미지 URL에서 버킷 내 경로와 파일 이름을 추출
        String image = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

        // 버킷 내의 image의 BlobId를 추출
        BlobId blobId = BlobId.of(firebaseBucket, image);

        // 이미지 삭제
        boolean deleted = bucket.getStorage().delete(blobId);

        if (deleted) {
            log.info("이미지 삭제 성공: {}", imageUrl);
        } else {
            log.error("이미지 삭제 실패: {}", imageUrl);
        }
    }
}
