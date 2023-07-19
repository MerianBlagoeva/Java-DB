package com.softuni.cardealer.service;

import com.softuni.cardealer.model.dto.seed.PartSeedDto;
import com.softuni.cardealer.model.entity.Part;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface PartService {
    void seedParts(List<PartSeedDto> parts);

    long getCount();

    Set<Part> findRandomParts();
}
