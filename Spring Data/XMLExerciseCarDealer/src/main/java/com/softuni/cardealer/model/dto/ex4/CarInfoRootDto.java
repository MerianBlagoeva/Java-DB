package com.softuni.cardealer.model.dto.ex4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarInfoRootDto {

    @XmlElement(name = "car")
    private List<CarInformationDto> cars;

    public List<CarInformationDto> getCars() {
        return cars;
    }

    public void setCars(List<CarInformationDto> cars) {
        this.cars = cars;
    }
}
