package com.fruit.mall.admin.image;

import lombok.Getter;

@Getter
public class FileInfo {
    private String firebaseImageUrl;
    private String fileName;

    public FileInfo(String firebaseImageUrl, String fileName) {
        this.firebaseImageUrl = firebaseImageUrl;
        this.fileName = fileName;
    }
}

