package com.softuni.cardealer.service.impl;

import com.google.gson.Gson;
import com.softuni.cardealer.model.dto.PartSeedDto;
import com.softuni.cardealer.model.entity.Part;
import com.softuni.cardealer.repository.PartRepository;
import com.softuni.cardealer.service.PartService;
import com.softuni.cardealer.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static com.softuni.cardealer.constants.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class PartServiceImpl implements PartService {

    private static final String PARTS_FILE_NAME = "parts.json";
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final SupplierService supplierService;

    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, Gson gson, SupplierService supplierService) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.supplierService = supplierService;
    }

    @Override
    public void seedParts() throws IOException {

        if (partRepository.count() > 0) {
            return;
        }
        String fileContent = Files
                .readString(Path.of(RESOURCES_FILE_PATH + PARTS_FILE_NAME));

        PartSeedDto[] partSeedDtos = gson
                .fromJson(fileContent, PartSeedDto[].class);

        Arrays.stream(partSeedDtos)
                .map(partSeedDto -> {
                    Part part = modelMapper.map(partSeedDto, Part.class);
                    part.setSupplier(supplierService.findRandomSupplier());

                    return part;
                })
                .forEach(partRepository::save);

    }

    @Override
    public Set<Part> findRandomParts() {
        Set<Part> partSet = new HashSet<>();

        int partsCount = ThreadLocalRandom
                .current().nextInt(3, 6);

        long totalPartsCount = partRepository.count();

        for (int i = 0; i < partsCount; i++) {
            long randomId = ThreadLocalRandom
                    .current().nextLong(1, totalPartsCount + 1);

            partSet.add(partRepository.findById(randomId).orElse(null));
        }

        return partSet;
    }

}
