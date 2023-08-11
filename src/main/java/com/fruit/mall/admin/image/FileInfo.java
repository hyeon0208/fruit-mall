package com.fruit.mall.admin.image;

import lombok.Getter;

@Getter
public class FileInfo {
    private Long imageId;
    private String firebaseImageUrl;
    private String fileName;

    public FileInfo(String firebaseImageUrl, String fileName) {
        this.firebaseImageUrl = firebaseImageUrl;
        this.fileName = fileName;
    }

    public FileInfo(Long imageId, String firebaseImageUrl, String fileName) {
        this.imageId = imageId;
        this.firebaseImageUrl = firebaseImageUrl;
        this.fileName = fileName;
    }
}

