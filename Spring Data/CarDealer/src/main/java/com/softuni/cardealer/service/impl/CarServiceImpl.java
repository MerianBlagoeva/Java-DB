package com.softuni.cardealer.service.impl;

import com.google.gson.Gson;
import com.softuni.cardealer.model.dto.CarSeedDto;
import com.softuni.cardealer.model.entity.Car;
import com.softuni.cardealer.repository.CarRepository;
import com.softuni.cardealer.service.CarService;
import com.softuni.cardealer.service.PartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static com.softuni.cardealer.constants.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class CarServiceImpl implements CarService {
    private static final String CARS_FILE_NAME = "cars.json";
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final PartService partService;

    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, Gson gson, PartService partService) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.partService = partService;
    }

    @Override
    public void seedCars() throws IOException {

        if (carRepository.count() > 0) {
            return;
        }

        String fileContent = Files
                .readString(Path.of(RESOURCES_FILE_PATH + CARS_FILE_NAME));

        CarSeedDto[] carSeedDtos = gson
                .fromJson(fileContent, CarSeedDto[].class);

        Arrays.stream(carSeedDtos)
                .map(carSeedDto -> {
                    Car car = modelMapper.map(carSeedDto, Car.class);
                    car.setParts(partService.findRandomParts());

                    return car;
                })
                .forEach(carRepository::save);
    }
}
