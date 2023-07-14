package com.softuni.cardealer;

import com.google.gson.Gson;
import com.softuni.cardealer.model.dto.*;
import com.softuni.cardealer.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;
    private final Gson gson;
    private final Scanner sc;

    private static final String OUTPUT_PATH = "src/main/resources/files/out/";

    private static final String ORDERED_CUSTOMERS_FILE_NAME = "ordered-customers.json";
    private static final String CARS_FROM_TOYOTA_FILE_NAME = "cars-from-toyota";

    private static final String SUPPLIERS_NOT_IMPORTERS_FILE_NAME = "suppliers-not-importers";
    private static final String CARS_WITH_THEIR_PARTS_FILE_NAME = "cars-with-their-parts";
    private static final String CUSTOMERS_WITH_BOUGHT_CARS_FILE_NAME = "customers-with-bought-cars";

    public CommandLineRunnerImpl(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService, Gson gson) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.gson = gson;
        sc = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        System.out.println("Enter query number:");
        int queryNumber = Integer.parseInt(sc.nextLine());

        switch (queryNumber) {
            case 1 -> orderedCustomers();
            case 2 -> carsFromMakeToyota();
            case 3 -> localSuppliers();
            case 4 -> carsWithTheirListOfParts();
            case 5 -> totalSalesByCustomer();
        }
    }

    private void totalSalesByCustomer() throws IOException {
        List<CustomerWithBoughtCarsDto> customersWithBoughtCarsDtos = customerService
                .findAllCustomersWithAtLeast1BoughtCarOrderByTotalMoneySpendDescThenByCarsBoughtDesc();

        String content = gson.toJson(customersWithBoughtCarsDtos);

        writeToFile(OUTPUT_PATH + CUSTOMERS_WITH_BOUGHT_CARS_FILE_NAME, content);
    }

    private void carsWithTheirListOfParts() throws IOException {

        List<CarAndPartsDto> carAndPartDtos = carService
                .findAllCarsWithTheirListOfParts();

        String content = gson.toJson(carAndPartDtos);

        writeToFile(OUTPUT_PATH + CARS_WITH_THEIR_PARTS_FILE_NAME, content);


    }

    private void localSuppliers() throws IOException {
        List<SupplierIdNameAndPartsCountDto> supplierDtos = supplierService
                .findAllSuppliersWhereIsImporterIsFalse(false);

        String content = gson.toJson(supplierDtos);

        writeToFile(OUTPUT_PATH + SUPPLIERS_NOT_IMPORTERS_FILE_NAME, content);
    }

    private void carsFromMakeToyota() throws IOException {
        List<CarFromToyotaDto> cars = carService
                .findAllCarsFromMakeToyotaOrderbyModelThenByTravelledDistanceAsc("Toyota");

        String content = gson.toJson(cars);

        writeToFile(OUTPUT_PATH + CARS_FROM_TOYOTA_FILE_NAME, content);
    }

    private void orderedCustomers() throws IOException {
        List<CustomerInfoDto> customerInfoDtos = customerService
                .findAllOrderByBirthDateAscThenByNotYoungDrivers();

        String content = gson.toJson(customerInfoDtos);

        writeToFile(OUTPUT_PATH + ORDERED_CUSTOMERS_FILE_NAME, content);


    }

    private void seedData() throws IOException {
        supplierService.seedSuppliers();
        partService.seedParts();
        carService.seedCars();
        customerService.seedCustomers();
        saleService.seedSales();

    }

    private void writeToFile(String filePath, String content) throws IOException {
        Files
                .write(Path.of(filePath), Collections.singleton(content));
    }
}
