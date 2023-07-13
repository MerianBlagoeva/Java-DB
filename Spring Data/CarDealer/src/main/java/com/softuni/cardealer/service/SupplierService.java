package com.softuni.cardealer.service;

import com.softuni.cardealer.model.entity.Supplier;

import java.io.IOException;

public interface SupplierService {
    void seedSuppliers() throws IOException;

    Supplier findRandomSupplier();
}
