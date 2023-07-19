package com.softuni.cardealer.service.impl;

import com.google.gson.Gson;
import com.softuni.cardealer.model.dto.ex1.CustomerInfoDto;
import com.softuni.cardealer.model.dto.ex5.CustomerWithBoughtCarsRootDto;
import com.softuni.cardealer.model.dto.seed.CustomerSeedDto;
import com.softuni.cardealer.model.dto.ex1.CustomerViewRootDto;
import com.softuni.cardealer.model.entity.Customer;
import com.softuni.cardealer.repository.CustomerRepository;
import com.softuni.cardealer.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMERS_FILE_NAME = "customers.xml";

    private final CustomerRepository customerRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, Gson gson, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public Customer findRandomCustomer() {
        long randomId = ThreadLocalRandom
                .current().nextLong(1, customerRepository.count() + 1);

        return customerRepository.findById(randomId).orElse(null);
    }

    @Override
    public void seedCustomers(List<CustomerSeedDto> customers){
        customers
                .stream()
                .map(customerSeedDto -> {
                    Customer customer = modelMapper.map(customerSeedDto, Customer.class);
                    customer.setBirthDate(LocalDateTime.parse(customerSeedDto.getBirthDate(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

                    return customer;
                })
                .forEach(customerRepository::save);
    }

    @Override
    public long getCount() {
        return customerRepository.count();
    }

    @Override
    public CustomerViewRootDto findAllOrderByBirthDateAscThenByNotYoungDrivers() {

        CustomerViewRootDto customerViewRootDto = new CustomerViewRootDto();

        List<Customer> customers = customerRepository.findAllByOrderByBirthDateAscIsYoungDriverDesc();

        List<CustomerInfoDto> customerInfoDtos = customers
                .stream()
                .map(customer -> {
                    CustomerInfoDto customerInfoDto = modelMapper.map(customer, CustomerInfoDto.class);
                    customerInfoDto.setBirthDate(customer.getBirthDate().toString());
                    return customerInfoDto;
                })
                .collect(Collectors.toList());

        customerViewRootDto.setCustomers(customerInfoDtos);

        return customerViewRootDto;


    }

    @Override
    public CustomerWithBoughtCarsRootDto findAllCustomersWithAtLeast1BoughtCarOrderByTotalMoneySpendDescThenByCarsBoughtDesc() {
        CustomerWithBoughtCarsRootDto customerRootDto = new CustomerWithBoughtCarsRootDto();

        customerRootDto.setCustomers(customerRepository
                .findAllWithAtLeastOneBoughtCarOrderByTotalMoneySpendDescThenByBoughtCarsDesc());

        return customerRootDto;
    }
}
