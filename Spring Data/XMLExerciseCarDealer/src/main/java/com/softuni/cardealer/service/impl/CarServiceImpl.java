package com.softuni.cardealer.service.impl;

import com.google.gson.Gson;
import com.softuni.cardealer.model.dto.ex2.CarFromToyotaDto;
import com.softuni.cardealer.model.dto.ex2.CarFromToyotaRootDto;
import com.softuni.cardealer.model.dto.ex4.CarInfoRootDto;
import com.softuni.cardealer.model.dto.ex4.CarInformationDto;
import com.softuni.cardealer.model.dto.ex4.PartNameAndPriceDto;
import com.softuni.cardealer.model.dto.seed.CarSeedDto;
import com.softuni.cardealer.model.entity.Car;
import com.softuni.cardealer.model.entity.Part;
import com.softuni.cardealer.repository.CarRepository;
import com.softuni.cardealer.service.CarService;
import com.softuni.cardealer.service.PartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private static final String CARS_FILE_NAME = "cars.xml";
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
    public void seedCars(List<CarSeedDto> cars) {
        cars
                .stream()
                .map(carSeedDto -> {
                    Car car = modelMapper.map(carSeedDto, Car.class);
                    car.setParts(partService.findRandomParts());

                    car.setPrice(BigDecimal.ZERO); // Initialize price to zero

                    BigDecimal totalPrice = BigDecimal.ZERO;
                    for (Part part : car.getParts()) {
                        totalPrice = totalPrice.add(part.getPrice());
                    }

                    car.setPrice(totalPrice);

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
    public CarFromToyotaRootDto findAllCarsFromMakeToyotaOrderbyModelThenByTravelledDistanceAsc(String make) {
        CarFromToyotaRootDto carFromToyotaRootDto = new CarFromToyotaRootDto();

        carFromToyotaRootDto.setCars(carRepository
                .findAllByMakeOrderByModelAscTravelledDistanceDesc(make)
                .stream()
                .map(car -> modelMapper.map(car, CarFromToyotaDto.class))
                .collect(Collectors.toList()));

        return carFromToyotaRootDto;
    }

    @Override
    public CarInfoRootDto findAllCarsWithTheirListOfParts() {
        List<Car> cars = carRepository.findAll();

        CarInfoRootDto carInfoRootDto = new CarInfoRootDto();

        carInfoRootDto.setCars(cars
                .stream()
                .map(car -> modelMapper.map(car, CarInformationDto.class))
                .collect(Collectors.toList()));

        return carInfoRootDto;

    }

    @Override
    public long getCount() {
        return carRepository.count();
    }
}
