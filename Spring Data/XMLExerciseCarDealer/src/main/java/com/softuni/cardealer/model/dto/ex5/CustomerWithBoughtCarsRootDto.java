package com.softuni.cardealer.model.dto.ex5;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerWithBoughtCarsRootDto {

    @XmlElement(name = "customer")
    private List<CustomerWithBoughtCarsDto> customers;

    public List<CustomerWithBoughtCarsDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerWithBoughtCarsDto> customers) {
        this.customers = customers;
    }
}
