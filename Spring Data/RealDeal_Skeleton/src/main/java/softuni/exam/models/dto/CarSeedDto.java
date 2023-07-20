package softuni.exam.models.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CarSeedDto {
    @Size(min = 2, max = 19)
    private String make;
    @Size(min = 2, max = 19)
    private String model;
    @Positive
    private Integer kilometers;
    private String registeredOn;


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


    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }
}
