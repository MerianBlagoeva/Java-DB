package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CarSeedRootDto;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarsRepository;
import softuni.exam.service.CarsService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CarsServiceImpl implements CarsService {
    private static String CARS_FILE_PATH = "src/main/resources/files/xml/cars.xml";
    private final CarsRepository carsRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public CarsServiceImpl(CarsRepository carsRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.carsRepository = carsRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return carsRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return Files.readString(Path.of(CARS_FILE_PATH));
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        StringBuilder output = new StringBuilder();

        xmlParser.fromFile(CARS_FILE_PATH, CarSeedRootDto.class)
                .getCars()
                .stream()
                .filter(carSeedDto -> {
                    boolean isForImport = validationUtil.isValid(carSeedDto)
                            && carsRepository.findByPlateNumber(carSeedDto.getPlateNumber()).isEmpty();

                    output.append(isForImport ? String.format("Successfully imported car %s - %s",
                            carSeedDto.getCarMake(), carSeedDto.getCarModel())
                            : "Invalid car")
                            .append(System.lineSeparator());

                    return isForImport;
                })
                .map(carSeedDto -> modelMapper.map(carSeedDto, Car.class))
                .forEach(carsRepository::save);

        return output.toString().trim();
    }

    @Override
    public Car findById(Long id) {
        return carsRepository.findById(id).orElse(null);
    }
}
