package com.softuni.cardealer.service;

import com.softuni.cardealer.model.dto.CarAndPartsDto;
import com.softuni.cardealer.model.dto.CarFromToyotaDto;
import com.softuni.cardealer.model.entity.Car;

import java.io.IOException;
import java.util.List;

public interface CarService {
    void seedCars() throws IOException;

    Car findRandomCar();

    List<CarFromToyotaDto> findAllCarsFromMakeToyotaOrderbyModelThenByTravelledDistanceAsc(String make);

    List<CarAndPartsDto> findAllCarsWithTheirListOfParts();
}
