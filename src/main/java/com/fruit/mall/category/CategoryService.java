package com.fruit.mall.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Long selectIdByCategoryName(String categoryName) {
        return categoryRepository.selectIdByCategoryName(categoryName);
    }
}
