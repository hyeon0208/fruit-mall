package com.fruit.mall.firebase;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class FireBaseService {
    private String firebaseBucket = "fruitmall-394009.appspot.com";

    public String uploadFiles(MultipartFile file, String path, String fileName) throws IOException {

        // 업로드 파일 유효성 검사
        if (file.isEmpty()) {
            log.error("File is Empty");
            return "File is Empty";
        }

        StringBuilder sb = new StringBuilder();
        if (!path.equals("")) {
            sb.append(path + "/");
        }
        sb.append(fileName);

        // Firebase Storage의 버킷을 가져온다.
        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);

        // MultipartFile을 바이트 배열로 변환하여 해당 파일의 내용을 가져온다.
        InputStream content = new ByteArrayInputStream(file.getBytes());

        // Firebase 스토리지에 파일 업로드. (업로드될 파일의 경로와 파일 이름, 파일의 내용, 파일 타입 )
        Blob blob = bucket.create(sb.toString(), content, file.getContentType());

        // 업로드된 파일의 다운로드 URL을 반환
        return blob.getMediaLink();
    }
}
