package com.fruit.mall.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepository implements CategoryMapper {
    private final CategoryMapper categoryMapper;

    @Override
    public Long selectIdByCategoryName(String categoryName) {
        return categoryMapper.selectIdByCategoryName(categoryName);
    }
}
