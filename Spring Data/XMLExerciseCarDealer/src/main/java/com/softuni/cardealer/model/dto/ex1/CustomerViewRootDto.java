package com.softuni.cardealer.model.dto.ex1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerViewRootDto {

    @XmlElement(name = "customer")
    private List<CustomerInfoDto> customers;

    public List<CustomerInfoDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerInfoDto> customers) {
        this.customers = customers;
    }
}
