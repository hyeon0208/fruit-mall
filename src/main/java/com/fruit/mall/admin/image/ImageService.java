package com.fruit.mall.admin.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public void insertImage(Image image) {
        imageRepository.insertImage(image);
    }

    public Image selectAllById(Long id) {
        return imageRepository.selectAllById(id);
    }

    public List<String> selectImageUrlByProductId(Long id) {
        return imageRepository.selectImageUrlByProductId(id);
    }
}
