package com.softuni.cardealer.service.impl;

import com.google.gson.Gson;
import com.softuni.cardealer.model.dto.*;
import com.softuni.cardealer.model.entity.Car;
import com.softuni.cardealer.model.entity.Part;
import com.softuni.cardealer.repository.CarRepository;
import com.softuni.cardealer.service.CarService;
import com.softuni.cardealer.service.PartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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

    @Override
    public Car findRandomCar() {
        long randomId = ThreadLocalRandom
                .current().nextLong(1, carRepository.count() + 1);

        return carRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<CarFromToyotaDto> findAllCarsFromMakeToyotaOrderbyModelThenByTravelledDistanceAsc(String make) {
        return carRepository
                .findAllByMakeOrderByModelAscTravelledDistanceDesc(make)
                .stream()
                .map(car -> modelMapper.map(car, CarFromToyotaDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarAndPartsDto> findAllCarsWithTheirListOfParts() {
        List<Car> cars = carRepository.findAll();

        List<CarAndPartsDto> carAndPartsDtos = new ArrayList<>();

        for (Car car : cars) {

            List<PartNameAndPriceDto> partNameAndPriceDtos = new ArrayList<>();

            for (Part part : car.getParts()) {
                PartNameAndPriceDto partNameAndPriceDto = modelMapper.map(part, PartNameAndPriceDto.class);
                partNameAndPriceDtos.add(partNameAndPriceDto);
            }

            CarInformationDto carInformationDto = modelMapper.map(car, CarInformationDto.class);

            CarAndPartsDto carAndPartsDto = new CarAndPartsDto();

            carAndPartsDto.setCar(carInformationDto);

            carAndPartsDtos.add(carAndPartsDto);

            carAndPartsDto.setParts(partNameAndPriceDtos);

        }

        return carAndPartsDtos;

    }
}
