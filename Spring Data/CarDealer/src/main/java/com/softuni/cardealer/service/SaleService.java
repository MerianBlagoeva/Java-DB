package com.softuni.cardealer.service;

import com.softuni.cardealer.model.dto.SaleInfoDto;

import java.util.List;

public interface SaleService {
    void seedSales();

    List<SaleInfoDto> findAllSalesInfo();
}
