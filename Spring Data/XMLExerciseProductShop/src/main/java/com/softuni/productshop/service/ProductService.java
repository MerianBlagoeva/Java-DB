package com.softuni.productshop.service;

import com.softuni.productshop.model.dto.ProductSeedDto;
import com.softuni.productshop.model.dto.ProductViewRootDto;

import java.util.List;

public interface ProductService {
    long getCount();

    void seedProducts(List<ProductSeedDto> products);

    ProductViewRootDto findProductsInRangeWithNoBuyer();
}
