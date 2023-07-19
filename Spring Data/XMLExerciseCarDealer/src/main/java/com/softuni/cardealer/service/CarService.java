package com.softuni.cardealer.service;

import com.softuni.cardealer.model.dto.ex2.CarFromToyotaRootDto;
import com.softuni.cardealer.model.dto.ex4.CarInfoRootDto;
import com.softuni.cardealer.model.dto.seed.CarSeedDto;
import com.softuni.cardealer.model.entity.Car;

import java.util.List;

public interface CarService {
    void seedCars(List<CarSeedDto> cars);

    Car findRandomCar();

    CarFromToyotaRootDto findAllCarsFromMakeToyotaOrderbyModelThenByTravelledDistanceAsc(String make);

    CarInfoRootDto findAllCarsWithTheirListOfParts();

    long getCount();
}
