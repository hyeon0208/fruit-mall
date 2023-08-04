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
    public void insertImage(Image image) {
        imageMapper.insertImage(image);
    }

    @Override
    public Image selectAllById(Long id) {
        return imageMapper.selectAllById(id);
    }
}
