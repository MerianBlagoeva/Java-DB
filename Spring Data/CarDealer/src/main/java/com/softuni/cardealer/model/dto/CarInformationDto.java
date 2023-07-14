package com.softuni.cardealer.model.dto;

import java.math.BigInteger;

public class CarInformationDto {
    private String Make;
    private String Model;
    private BigInteger TravelledDistance;

    public CarInformationDto() {
    }

    public String getMake() {
        return Make;
    }

    public void setMake(String make) {
        Make = make;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public BigInteger getTravelledDistance() {
        return TravelledDistance;
    }

    public void setTravelledDistance(BigInteger travelledDistance) {
        TravelledDistance = travelledDistance;
    }
}
