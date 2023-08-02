package com.fruit.mall.admin.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService implements ImageMapper {
    private final ImageMapper imageMapper;

    @Override
    public void insertImage(Long productId, String imageUrl, String imagePath, String imageFilename) {
        imageMapper.insertImage(productId, imageUrl, imagePath, imageFilename);
    }
}
