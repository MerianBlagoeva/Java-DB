package com.softuni.cardealer.service.impl;

import com.google.gson.Gson;
import com.softuni.cardealer.model.dto.SupplierIdNameAndPartsCountDto;
import com.softuni.cardealer.model.dto.SupplierSeedDto;
import com.softuni.cardealer.model.entity.Supplier;
import com.softuni.cardealer.repository.SupplierRepository;
import com.softuni.cardealer.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.softuni.cardealer.constants.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private static final String SUPPLIERS_FILE_NAME = "suppliers.json";

    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, Gson gson) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedSuppliers() throws IOException {

        if (supplierRepository.count() > 0) {
            return;
        }

        String fileContent = Files
                .readString(Path.of(RESOURCES_FILE_PATH + SUPPLIERS_FILE_NAME));


        SupplierSeedDto[] supplierSeedDtos = gson
                .fromJson(fileContent, SupplierSeedDto[].class);


        Arrays.stream(supplierSeedDtos)
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
    public List<SupplierIdNameAndPartsCountDto> findAllSuppliersWhereIsImporterIsFalse(Boolean isImporter) {
        return supplierRepository
                .findAllByIsImporter(isImporter)
                .stream()
                .map(supplier -> {
                    SupplierIdNameAndPartsCountDto supplierDto = modelMapper.map(supplier, SupplierIdNameAndPartsCountDto.class);
                    supplierDto.setPartsCount(supplier.getParts().size());

                    return supplierDto;
                })
                .collect(Collectors.toList());
    }
}
