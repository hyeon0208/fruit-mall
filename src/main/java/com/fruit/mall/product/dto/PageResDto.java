package com.fruit.mall.product.dto;

import com.fruit.mall.config.SessionUser;
import com.fruit.mall.product.Product;
import com.github.pagehelper.PageInfo;
import lombok.Getter;

@Getter
public class PageResDto {
    private PageInfo<Product> productPageInfo;
    private PageInfo<ProductAndImageInfo> productAndImageInfoPageInfo;
    private String status;
    private String category;
    private Long userId;

    public PageResDto(PageInfo<Product> pageInfo, String status, String category) {
        this.productPageInfo = pageInfo;
        this.status = status;
        this.category = category;
    }

    public PageResDto(PageInfo<ProductAndImageInfo> pageInfo, String category, Long userId) {
        this.productAndImageInfoPageInfo = pageInfo;
        this.category = category;
        this.userId = userId;
    }
}
