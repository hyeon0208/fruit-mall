package com.fruit.mall.admin.product.dto;

import com.fruit.mall.admin.product.Product;
import com.github.pagehelper.PageInfo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class PageResDto {
    private PageInfo<Product> pageInfo;
    private String status;
    private String category;

    @Builder
    public PageResDto(PageInfo<Product> pageInfo, String status, String category) {
        this.pageInfo = pageInfo;
        this.status = status;
        this.category = category;
    }
}
