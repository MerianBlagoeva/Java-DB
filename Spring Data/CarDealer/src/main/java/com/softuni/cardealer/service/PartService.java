package com.softuni.cardealer.service;

import com.softuni.cardealer.model.entity.Part;

import java.io.IOException;
import java.util.Set;

public interface PartService {
    void seedParts() throws IOException;

    Set<Part> findRandomParts();
}
