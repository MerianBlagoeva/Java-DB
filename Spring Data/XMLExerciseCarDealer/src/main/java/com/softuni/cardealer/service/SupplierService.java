package com.softuni.cardealer.service;

import com.softuni.cardealer.model.dto.ex3.SupplierIdNameAndPartsCountRootDto;
import com.softuni.cardealer.model.dto.seed.SupplierSeedDto;
import com.softuni.cardealer.model.entity.Supplier;

import java.util.List;

public interface SupplierService {
    void seedSuppliers(List<SupplierSeedDto> suppliers);

    Supplier findRandomSupplier();

    SupplierIdNameAndPartsCountRootDto findAllSuppliersWhereIsImporterIsFalse();

    long getCount();
}
