package com.fruit.mall.admin.image;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
public class Image {
    private Long imageId;
    private Long productId;
    private String imageUrl;
    private String path;
    private String fileName;
    private Timestamp imageUploadedAt;

    @Builder
    public Image(Long productId, String imageUrl, String path, String fileName, Timestamp imageUploadedAt) {
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.path = path;
        this.fileName = fileName;
        this.imageUploadedAt = imageUploadedAt;
    }
}
