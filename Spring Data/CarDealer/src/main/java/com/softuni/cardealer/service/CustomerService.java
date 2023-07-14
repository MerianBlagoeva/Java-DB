package com.softuni.cardealer.service;

import com.softuni.cardealer.model.dto.CustomerInfoDto;
import com.softuni.cardealer.model.dto.CustomerWithBoughtCarsDto;
import com.softuni.cardealer.model.entity.Customer;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    Customer findRandomCustomer();

    void seedCustomers() throws IOException;

    List<CustomerInfoDto> findAllOrderByBirthDateAscThenByNotYoungDrivers();

    List<CustomerWithBoughtCarsDto> findAllCustomersWithAtLeast1BoughtCarOrderByTotalMoneySpendDescThenByCarsBoughtDesc();
}
