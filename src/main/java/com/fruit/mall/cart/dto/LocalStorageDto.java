package com.fruit.mall.cart.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class LocalStorageDto {
    private List<LocalCart> localCarts;
}
