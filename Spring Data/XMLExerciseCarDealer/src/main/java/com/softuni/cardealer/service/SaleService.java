package com.softuni.cardealer.service;

import com.softuni.cardealer.model.dto.ex6.SaleInfoRootDto;

public interface SaleService {
    void seedSales();

    SaleInfoRootDto findAllSales();
}
