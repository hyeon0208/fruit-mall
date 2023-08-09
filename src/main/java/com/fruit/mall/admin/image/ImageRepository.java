package com.fruit.mall.admin.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageRepository implements ImageMapper {

    private final ImageMapper imageMapper;

    @Override
    public void insertImage(Image image) {
        imageMapper.insertImage(image);
    }

    @Override
    public Image selectAllById(Long id) {
        return imageMapper.selectAllById(id);
    }

    @Override
    public List<String> selectImageUrlByProductId(Long id) {
        return imageMapper.selectImageUrlByProductId(id);
    }
}
