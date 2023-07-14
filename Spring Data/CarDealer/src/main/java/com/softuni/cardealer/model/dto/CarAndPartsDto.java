package com.softuni.cardealer.model.dto;

import java.util.List;

public class CarAndPartsDto {
    private CarInformationDto car;

    private List<PartNameAndPriceDto> parts;

    public CarAndPartsDto() {
    }

    public CarInformationDto getCar() {
        return car;
    }

    public void setCar(CarInformationDto car) {
        this.car = car;
    }

    public List<PartNameAndPriceDto> getParts() {
        return parts;
    }

    public void setParts(List<PartNameAndPriceDto> parts) {
        this.parts = parts;
    }
}
