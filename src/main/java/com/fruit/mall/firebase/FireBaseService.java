package com.fruit.mall.firebase;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
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


    public UploadResult uploadFiles(MultipartFile file, String path, String fileName) throws IOException {

        // 업로드 파일 유효성 검사
        if (file.isEmpty()) {
            log.error("File is Empty");
            return new UploadResult();
        }

        // Firebase Storage의 버킷을 가져온다.
        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);

        // 중복된 파일이름이 있으면 파일이름 뒤에 (+1) 씩 숫자를 붙인다. ex:) 키위.jpeg, 키위(1).jpeg, 키위(2).jpeg
        StringBuilder sb = new StringBuilder();
        sb.append(path);
        sb.append("/");

        String newFileName = fileName;
        int duplicateCount = 0;

        while (true) {
            if (duplicateCount > 0) {
                String ext = fileName.substring(fileName.lastIndexOf("."));
                String name = fileName.substring(0, fileName.lastIndexOf("."));
                newFileName = String.format("%s(%d)%s", name, duplicateCount, ext);
            }
            sb.setLength(path.length() + 1);
            sb.append(newFileName);

            BlobInfo blobInfo = BlobInfo.newBuilder(bucket.getName(), sb.toString()).build();
            if (bucket.getStorage().get(blobInfo.getBlobId()) != null) {
                duplicateCount++;
            } else {
                break;
            }
        }

        // MultipartFile을 바이트 배열로 변환하여 해당 파일의 내용을 가져온다.
        InputStream content = new ByteArrayInputStream(file.getBytes());

        // Firebase 스토리지에 파일 업로드. (업로드될 파일의 경로와 파일 이름, 파일의 내용, 파일 타입 )
        Blob blob = bucket.create(sb.toString(), content, file.getContentType());

        log.info("이미지 저장 경로 {}", sb);

        // 업로드된 파일의 다운로드 URL을 반환
        return new UploadResult(blob.getMediaLink(), newFileName);
    }

    public boolean isStoredImage(String path, String fileName) {
        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);
        Page<Blob> blobs = bucket.list();

        for (Blob blob : blobs.iterateAll()) {
            if (blob.getName().equals(path + "/" +fileName)) {
                log.info("저장된 이미지가 있습니다. : {}", blob);
                return true;
            }
        }
        log.info("저장된 이미지가 없습니다.");
        return false;
    }


    public void deleteStoredImage(String path, String fileName) {
        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);
        Page<Blob> blobs = bucket.list();

        for (Blob blob : blobs.iterateAll()) {
            if (blob.getName().equals(path + "/" +fileName)) {
                log.info("삭제할 이미지를 찾았습니다. : {}", blob);

                boolean deleted = blob.delete();
                if (deleted) {
                    log.info("이미지가 성공적으로 삭제되었습니다.");
                }
            }
        }
        log.info("삭제할 이미지가 없습니다.");
    }
}
