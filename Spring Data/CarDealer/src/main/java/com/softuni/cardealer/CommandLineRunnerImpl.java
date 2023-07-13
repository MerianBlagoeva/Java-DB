package com.softuni.cardealer;

import com.softuni.cardealer.service.CarService;
import com.softuni.cardealer.service.PartService;
import com.softuni.cardealer.service.SupplierService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final SupplierService supplierService;
    private final PartService partService;

    private final CarService carService;

    public CommandLineRunnerImpl(SupplierService supplierService, PartService partService, CarService carService) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
    }

    private void seedData() throws IOException {
        supplierService.seedSuppliers();
        partService.seedParts();
        carService.seedCars();

    }
}
