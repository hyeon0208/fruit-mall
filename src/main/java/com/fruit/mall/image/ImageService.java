package com.fruit.mall.image;

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

    public void updateImage(Image image) {
        imageRepository.updateImage(image);
    }

    public void deleteImagesByProductId(Long id) {
        imageRepository.deleteImagesByProductId(id);
    }

    public String selectProductImageUrlByProductId(Long id) {
        return imageRepository.selectProductImageUrlByProductId(id);
    }

    public List<String> selectFileNamesByProductId(Long id) {
        return imageRepository.selectFileNamesByProductId(id);
    }

    public List<Image> selectImagesByProductId(Long id) {
        return imageRepository.selectImagesByProductId(id);
    }
}
