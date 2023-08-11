package com.fruit.mall.firebase;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UploadResult {
    private String firebaseImageUrl;
    private String fileName;

    public UploadResult(String firebaseImageUrl, String fileName) {
        this.firebaseImageUrl = firebaseImageUrl;
        this.fileName = fileName;
    }
}
