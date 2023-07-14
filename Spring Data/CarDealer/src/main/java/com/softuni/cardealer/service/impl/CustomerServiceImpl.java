package com.softuni.cardealer.service.impl;

import com.google.gson.Gson;
import com.softuni.cardealer.model.dto.CustomerInfoDto;
import com.softuni.cardealer.model.dto.CustomerSeedDto;
import com.softuni.cardealer.model.dto.CustomerWithBoughtCarsDto;
import com.softuni.cardealer.model.entity.Car;
import com.softuni.cardealer.model.entity.Customer;
import com.softuni.cardealer.model.entity.Part;
import com.softuni.cardealer.model.entity.Sale;
import com.softuni.cardealer.repository.CustomerRepository;
import com.softuni.cardealer.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.softuni.cardealer.constants.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMERS_FILE_NAME = "customers.json";

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
    public void seedCustomers() throws IOException {
        String fileContent = Files
                .readString(Path.of(RESOURCES_FILE_PATH + CUSTOMERS_FILE_NAME));

        CustomerSeedDto[] customerSeedDtos = gson
                .fromJson(fileContent, CustomerSeedDto[].class);

        Arrays.stream(customerSeedDtos)
                .map(customerSeedDto -> {
                    Customer customer = modelMapper.map(customerSeedDto, Customer.class);
                    customer.setBirthDate(LocalDate.parse(customerSeedDto.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

                    return customer;
                })
                .forEach(customerRepository::save);


    }

    @Override
    public List<CustomerInfoDto> findAllOrderByBirthDateAscThenByNotYoungDrivers() {
        return customerRepository
                .findAllByOrderByBirthDateAscIsYoungDriverDesc()
                .stream()
                .map(customer -> {
                    CustomerInfoDto customerInfoDto = modelMapper.map(customer, CustomerInfoDto.class);
                    customerInfoDto.setBirthDate(customer.getBirthDate().toString());
                    return customerInfoDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerWithBoughtCarsDto> findAllCustomersWithAtLeast1BoughtCarOrderByTotalMoneySpendDescThenByCarsBoughtDesc() {


        return customerRepository.
                findAllWithAtLeastOneBoughtCarOrderByTotalMoneySpendDescThenByBoughtCarsDesc();
    }
}
