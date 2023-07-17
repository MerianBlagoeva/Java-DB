package com.softuni.cardealer.service.impl;

import com.softuni.cardealer.model.dto.SaleSeedDto;
import com.softuni.cardealer.model.entity.Sale;
import com.softuni.cardealer.repository.SaleRepository;
import com.softuni.cardealer.service.CarService;
import com.softuni.cardealer.service.CustomerService;
import com.softuni.cardealer.service.SaleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final CarService carService;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    public SaleServiceImpl(SaleRepository saleRepository, CarService carService, CustomerService customerService, ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.carService = carService;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedSales() {

        if (saleRepository.count() > 0) {
            return;
        }


        SaleSeedDto[] saleSeedDtos = new SaleSeedDto[50];

        for (int i = 0; i < 50; i++) {
            saleSeedDtos[i] = new SaleSeedDto();
        }

        List<Integer> discountList = List.of(0, 5, 10, 15, 20, 30, 40, 50);

        Arrays.stream(saleSeedDtos)
                .forEach(saleSeedDto -> {
                    saleSeedDto.setCar(carService.findRandomCar());
                    saleSeedDto.setCustomer(customerService.findRandomCustomer());
                    int randomIndex = ThreadLocalRandom.current().nextInt(0, discountList.size());
                    int discount = discountList.get(randomIndex);
                    saleSeedDto.setDiscount(discount);
                });

        Arrays.stream(saleSeedDtos)
                .map(saleSeedDto -> modelMapper.map(saleSeedDto, Sale.class))
                .forEach(saleRepository::save);
    }
}
