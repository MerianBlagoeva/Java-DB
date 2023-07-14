package com.softuni.productshop.model.dto;

import com.google.gson.annotations.Expose;

import java.util.List;

public class SoldProductsDto {
    @Expose
    private Integer count;
    @Expose
    private List<ProductInfoDto> products;

    public SoldProductsDto() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProductInfoDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInfoDto> products) {
        this.products = products;
    }
}
