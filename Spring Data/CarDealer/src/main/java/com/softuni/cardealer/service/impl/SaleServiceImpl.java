package com.softuni.cardealer.service.impl;

import com.softuni.cardealer.model.dto.SaleInfoDto;
import com.softuni.cardealer.model.dto.SaleSeedDto;
import com.softuni.cardealer.model.entity.Sale;
import com.softuni.cardealer.repository.SaleRepository;
import com.softuni.cardealer.service.CarService;
import com.softuni.cardealer.service.CustomerService;
import com.softuni.cardealer.service.SaleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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

        List<Double> discountList = List.of(0.0, 0.05, 0.1, 0.15, 0.2, 0.3, 0.4, 0.5);

        Arrays.stream(saleSeedDtos)
                .forEach(saleSeedDto -> {
                    saleSeedDto.setCar(carService.findRandomCar());
                    saleSeedDto.setCustomer(customerService.findRandomCustomer());
                    int randomIndex = ThreadLocalRandom.current().nextInt(0, discountList.size());
                    Double discount = discountList.get(randomIndex);
                    saleSeedDto.setDiscount(discount);
                });

        Arrays.stream(saleSeedDtos)
                .map(saleSeedDto -> modelMapper.map(saleSeedDto, Sale.class))
                .forEach(saleRepository::save);
    }

    @Override
    public List<SaleInfoDto> findAllSalesInfo() {

        return saleRepository.findAll()
                .stream()
                .map(sale ->  {
                    SaleInfoDto saleInfoDto = modelMapper.map(sale, SaleInfoDto.class);
                    saleInfoDto.setPriceWithDiscount(sale.getCar().getPrice().subtract(sale.getCar().getPrice().multiply(BigDecimal.valueOf(sale.getDiscount()))));

                    return saleInfoDto;
                })
                .collect(Collectors.toList());
    }
}
