package com.fruit.mall.admin.image;

import com.fruit.mall.firebase.FireBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ImageController {
    private final FireBaseService fireBseService;

    @PostMapping("/firebase/upload")
    @ResponseBody
    public List<String> uploadImageToFirebase(@RequestParam("file") List<MultipartFile> files, @RequestParam("path") String path, @RequestParam("fileName") String fileName) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                String imageUrl = fireBseService.uploadFiles(file, path, file.getOriginalFilename());
                imageUrls.add(imageUrl);
            } catch (IOException e) {
                // Handle exception if needed
            }
        }
        return imageUrls;
    }
}
