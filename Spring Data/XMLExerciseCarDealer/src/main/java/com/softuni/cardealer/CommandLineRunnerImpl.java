package com.softuni.cardealer;

import com.softuni.cardealer.model.dto.ex1.CustomerViewRootDto;
import com.softuni.cardealer.model.dto.ex2.CarFromToyotaRootDto;
import com.softuni.cardealer.model.dto.ex3.SupplierIdNameAndPartsCountRootDto;
import com.softuni.cardealer.model.dto.ex4.CarInfoRootDto;
import com.softuni.cardealer.model.dto.ex5.CustomerWithBoughtCarsRootDto;
import com.softuni.cardealer.model.dto.ex6.SaleInfoRootDto;
import com.softuni.cardealer.model.dto.seed.*;
import com.softuni.cardealer.service.*;
import com.softuni.cardealer.util.XmlParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Scanner;

import static com.softuni.cardealer.constants.GlobalConstants.RESOURCES_FILE_PATH;
import static com.softuni.cardealer.constants.GlobalConstants.RESOURCES_OUT_PATH;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private static final String SUPPLIERS_FILE_NAME = "suppliers.xml";
    private static final String PARTS_FILE_NAME = "parts.xml";
    private static final String CARS_FILE_NAME = "cars.xml";
    private static final String CUSTOMERS_FILE_NAME = "customers.xml";
    private static final String CARS_FROM_TOYOTA_FILE_NAME = "cars-from-toyota.xml";
    private static final String NOT_IMPORTER_SUPPLIERS_FILE_NAME = "not-importer-suppliers.xml";
    private static final String CARS_WITH_THEIR_PARTS_FILE_NAME = "cars-with-their-parts.xml";
    private static final String TOTAL_SALES_BY_CUSTOMER_FILE_NAME = "total-sales-by-customer.xml";
    private static final String SALES_WITH_DISCOUNT_FILE_NAME = "sales-with-discount.xml";
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final SaleService saleService;
    private final Scanner sc;
    private final CustomerService customerService;
    private final XmlParser xmlParser;
    private static final String ORDERED_CUSTOMERS_FILE_NAME = "ordered-customers.xml";

    public CommandLineRunnerImpl(SupplierService supplierService, PartService partService, CarService carService, SaleService saleService, CustomerService customerService, XmlParser xmlParser) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.saleService = saleService;
        this.customerService = customerService;
        this.xmlParser = xmlParser;
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
            case 6 -> salesWithAppliedDiscount();
        }
    }

    private void salesWithAppliedDiscount() throws JAXBException {
        SaleInfoRootDto saleInfoRootDto = saleService
                .findAllSales();

        xmlParser.writeToFile(RESOURCES_OUT_PATH + SALES_WITH_DISCOUNT_FILE_NAME, saleInfoRootDto);
    }

    private void totalSalesByCustomer() throws JAXBException {
        CustomerWithBoughtCarsRootDto customerRootDto = customerService
                .findAllCustomersWithAtLeast1BoughtCarOrderByTotalMoneySpendDescThenByCarsBoughtDesc();

        xmlParser.writeToFile(RESOURCES_OUT_PATH + TOTAL_SALES_BY_CUSTOMER_FILE_NAME,
                customerRootDto);
    }

    private void carsWithTheirListOfParts() throws JAXBException {
        CarInfoRootDto carInfoRootDto = carService.findAllCarsWithTheirListOfParts();

        xmlParser.writeToFile(RESOURCES_OUT_PATH + CARS_WITH_THEIR_PARTS_FILE_NAME,
                carInfoRootDto);
    }

    private void localSuppliers() throws JAXBException {
        SupplierIdNameAndPartsCountRootDto supplierRootDto = supplierService
                .findAllSuppliersWhereIsImporterIsFalse();

        xmlParser.writeToFile(RESOURCES_OUT_PATH + NOT_IMPORTER_SUPPLIERS_FILE_NAME, supplierRootDto);
    }

    private void carsFromMakeToyota() throws JAXBException {
        CarFromToyotaRootDto carRootDto = carService
                .findAllCarsFromMakeToyotaOrderbyModelThenByTravelledDistanceAsc("Toyota");

        xmlParser.writeToFile(RESOURCES_OUT_PATH + CARS_FROM_TOYOTA_FILE_NAME,
                carRootDto);
    }

    private void seedData() throws IOException, JAXBException {
        if (supplierService.getCount() == 0) {
            SupplierSeedRootDto supplierSeedRootDto = xmlParser
                    .fromFile(RESOURCES_FILE_PATH + SUPPLIERS_FILE_NAME,
                            SupplierSeedRootDto.class);

            supplierService.seedSuppliers(supplierSeedRootDto.getSuppliers());
        }

        if (partService.getCount() == 0) {
            PartSeedRootDto partSeedRootDto = xmlParser
                    .fromFile(RESOURCES_FILE_PATH + PARTS_FILE_NAME,
                            PartSeedRootDto.class);

            partService.seedParts(partSeedRootDto.getParts());
        }


        if (carService.getCount() == 0) {
            CarSeedRootDto carSeedRootDto = xmlParser
                    .fromFile(RESOURCES_FILE_PATH + CARS_FILE_NAME,
                            CarSeedRootDto.class);

            carService.seedCars(carSeedRootDto.getCars());
        }

        if (customerService.getCount() == 0) {
            CustomerSeedRootDto customerSeedRootDto = xmlParser
                    .fromFile(RESOURCES_FILE_PATH + CUSTOMERS_FILE_NAME,
                            CustomerSeedRootDto.class);

            customerService.seedCustomers(customerSeedRootDto.getCustomers());
        }

        saleService.seedSales();

    }

    private void orderedCustomers() throws JAXBException {
        CustomerViewRootDto customerViewRootDto =
                customerService.findAllOrderByBirthDateAscThenByNotYoungDrivers();

        xmlParser.writeToFile(RESOURCES_OUT_PATH + ORDERED_CUSTOMERS_FILE_NAME,
                customerViewRootDto);


    }
}
