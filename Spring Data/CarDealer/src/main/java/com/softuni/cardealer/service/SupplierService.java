package com.softuni.cardealer.service;

import com.softuni.cardealer.model.dto.SupplierIdNameAndPartsCountDto;
import com.softuni.cardealer.model.entity.Supplier;

import java.io.IOException;
import java.util.List;

public interface SupplierService {
    void seedSuppliers() throws IOException;

    Supplier findRandomSupplier();

    List<SupplierIdNameAndPartsCountDto> findAllSuppliersWhereIsImporterIsFalse(Boolean isImporter);
}
