package com.fruit.mall.admin.product.dto;

import com.fruit.mall.admin.product.Product;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResDto {
    private List<Product> products;

    public PageResDto(List<Product> products) {
        this.products = products;
    }
}