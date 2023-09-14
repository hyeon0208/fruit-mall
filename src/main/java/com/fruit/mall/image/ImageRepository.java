package com.fruit.mall.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageRepository implements ImageMapper {
    private final ImageMapper imageMapper;

    @Override
    public List<String> selectFileNamesByProductId(Long id) {
        return imageMapper.selectFileNamesByProductId(id);
    }

    @Override
    public String selectProductImageUrlByProductId(Long id) {
        return imageMapper.selectProductImageUrlByProductId(id);
    }

    @Override
    public List<Image> selectImagesByProductId(Long id) {
        return imageMapper.selectImagesByProductId(id);
    }
}
