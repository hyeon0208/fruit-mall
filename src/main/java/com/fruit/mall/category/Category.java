package com.fruit.mall.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Category {
    private Long categoryId;
    private String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
