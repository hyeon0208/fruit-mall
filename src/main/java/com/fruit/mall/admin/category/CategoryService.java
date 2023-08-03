package com.fruit.mall.admin.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService implements CategoryMapper {
    private final CategoryMapper categoryMapper;

    @Override
    public Long selectIdByCategoryName(String categoryName) {
        return categoryMapper.selectIdByCategoryName(categoryName);
    }
}
