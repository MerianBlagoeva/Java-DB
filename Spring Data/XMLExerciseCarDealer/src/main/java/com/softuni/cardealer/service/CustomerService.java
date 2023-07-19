package com.softuni.cardealer.service;

import com.softuni.cardealer.model.dto.ex5.CustomerWithBoughtCarsDto;
import com.softuni.cardealer.model.dto.ex1.CustomerViewRootDto;
import com.softuni.cardealer.model.dto.ex5.CustomerWithBoughtCarsRootDto;
import com.softuni.cardealer.model.dto.seed.CustomerSeedDto;
import com.softuni.cardealer.model.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer findRandomCustomer();

    void seedCustomers(List<CustomerSeedDto> customers);

    long getCount();

    CustomerViewRootDto findAllOrderByBirthDateAscThenByNotYoungDrivers();

    CustomerWithBoughtCarsRootDto findAllCustomersWithAtLeast1BoughtCarOrderByTotalMoneySpendDescThenByCarsBoughtDesc();
}
