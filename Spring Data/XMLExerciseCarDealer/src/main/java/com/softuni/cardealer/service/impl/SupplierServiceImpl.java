package com.softuni.cardealer.service.impl;

import com.softuni.cardealer.model.dto.ex3.SupplierIdNameAndPartsCountDto;
import com.softuni.cardealer.model.dto.ex3.SupplierIdNameAndPartsCountRootDto;
import com.softuni.cardealer.model.dto.seed.SupplierSeedDto;
import com.softuni.cardealer.model.entity.Supplier;
import com.softuni.cardealer.repository.SupplierRepository;
import com.softuni.cardealer.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    private static final String SUPPLIERS_FILE_NAME = "suppliers.xml";

    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedSuppliers(List<SupplierSeedDto> suppliers) {

        suppliers
                .stream()
                .map(supplierSeedDto -> modelMapper.map(supplierSeedDto, Supplier.class))
                .forEach(supplierRepository::save);
    }

    @Override
    public Supplier findRandomSupplier() {
        long randomId = ThreadLocalRandom
                .current().nextLong(1, supplierRepository.count() + 1);

        return supplierRepository.findById(randomId)
                .orElse(null);
    }

    @Override
    public SupplierIdNameAndPartsCountRootDto findAllSuppliersWhereIsImporterIsFalse() {

        SupplierIdNameAndPartsCountRootDto supplierIdNameAndPartsCountRootDto = new SupplierIdNameAndPartsCountRootDto();
        supplierIdNameAndPartsCountRootDto.setSuppliers(supplierRepository
                .findAllByIsImporterIsFalse()
                .stream()
                .map(supplier -> {
                    SupplierIdNameAndPartsCountDto supplierDto = modelMapper.map(supplier, SupplierIdNameAndPartsCountDto.class);
                    supplierDto.setPartsCount(supplier.getParts().size());

                    return supplierDto;
                })
                .collect(Collectors.toList()));

        return supplierIdNameAndPartsCountRootDto;
    }

    @Override
    public long getCount() {
        return supplierRepository.count();
    }
}
