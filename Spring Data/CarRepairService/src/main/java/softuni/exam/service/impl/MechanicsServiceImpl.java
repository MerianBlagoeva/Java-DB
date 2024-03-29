package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.MechanicSeedDto;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.service.MechanicsService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;


@Service
public class MechanicsServiceImpl implements MechanicsService {
    private static final String MECHANICS_FILE_PATH = "src/main/resources/files/json/mechanics.json";
    private final MechanicsRepository mechanicsRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public MechanicsServiceImpl(MechanicsRepository mechanicsRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.mechanicsRepository = mechanicsRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return mechanicsRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(Path.of(MECHANICS_FILE_PATH));
    }

    @Override
    public String importMechanics() throws IOException {
        StringBuilder output = new StringBuilder();

        Arrays.stream(gson
                .fromJson(readMechanicsFromFile(), MechanicSeedDto[].class))
                .filter(mechanicSeedDto -> {
                    boolean isForImport = validationUtil.isValid(mechanicSeedDto)
                            && mechanicsRepository.findByEmail(mechanicSeedDto.getEmail()).isEmpty();

                    output.append(isForImport ? String.format("Successfully imported mechanic %s %s",
                            mechanicSeedDto.getFirstName(), mechanicSeedDto.getLastName())
                            : "Invalid mechanic")
                            .append(System.lineSeparator());

                    return isForImport;
                })
                .map(mechanicSeedDto -> modelMapper.map(mechanicSeedDto, Mechanic.class))
                .forEach(mechanicsRepository::save);

        return output.toString().trim();
    }

    @Override
    public Mechanic findMechanicByName(String firstName) {
        return mechanicsRepository.findByFirstName(firstName);
    }
}
