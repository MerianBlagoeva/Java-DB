package com.softuni.cardealer.model.dto.ex4;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;
import java.util.List;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarInformationDto {
    @XmlAttribute(name = "make")
    private String make;

    @XmlAttribute(name = "model")
    private String model;
    @XmlAttribute(name = "travelled-distance")
    private BigInteger travelledDistance;

    @XmlElement(name = "part")
    @XmlElementWrapper(name = "parts")
    private List<PartNameAndPriceDto> parts;

    public CarInformationDto() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigInteger getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(BigInteger travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public List<PartNameAndPriceDto> getParts() {
        return parts;
    }

    public void setParts(List<PartNameAndPriceDto> parts) {
        this.parts = parts;
    }
}
