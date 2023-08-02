package com.fruit.mall.admin.image;

import com.fruit.mall.firebase.FireBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final FireBaseService fireBseService;

    @PostMapping("/upload")
    @ResponseBody
    public String uploadImage(@RequestParam("file")  MultipartFile file, @RequestParam("path") String path, @RequestParam("fileName") String fileName) throws IOException {
        try {
            String imageUrl = fireBseService.uploadFiles(file, path, fileName);
            return imageUrl;
        } catch (IOException e) {
            return "fail";
        }
    }
}
