package com.fruit.mall.admin.image;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class Image {
    private Long imageId;
    private Long productId;
    private String imageUrl;
    private String imagePath;
    private String imageFilename;
    private String imageSize;
    private String imageDescription;
    private String imageType;
    private Timestamp imageUploadedAt;

    @Builder
    public Image(Long productId, String imageUrl, String imagePath, String imageFilename, String imageSize, String imageDescription, String imageType, Timestamp imageUploadedAt) {
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.imagePath = imagePath;
        this.imageFilename = imageFilename;
        this.imageSize = imageSize;
        this.imageDescription = imageDescription;
        this.imageType = imageType;
        this.imageUploadedAt = imageUploadedAt;
    }
}
