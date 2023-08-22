package com.fruit.mall.admin.product.dto;

import com.fruit.mall.admin.product.Product;
import com.github.pagehelper.PageInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PageResDto {
    private PageInfo<Product> productPageInfo;
    private PageInfo<ProductAndImageInfo> productAndImageInfoPageInfo;
    private String status;
    private String category;

    public PageResDto(PageInfo<Product> pageInfo, String status, String category) {
        this.productPageInfo = pageInfo;
        this.status = status;
        this.category = category;
    }

    public PageResDto(PageInfo<ProductAndImageInfo> pageInfo, String category) {
        this.productAndImageInfoPageInfo = pageInfo;
        this.category = category;
    }
}
