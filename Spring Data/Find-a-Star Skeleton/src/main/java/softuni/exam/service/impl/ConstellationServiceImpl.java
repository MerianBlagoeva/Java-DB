package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ConstellationSeedDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class ConstellationServiceImpl implements ConstellationService {
    private static final String CONSTELLATIONS_FILE_PATH = "src/main/resources/files/json/constellations.json";
    private final ConstellationRepository constellationRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public ConstellationServiceImpl(ConstellationRepository constellationRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.constellationRepository = constellationRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return Files.readString(Path.of(CONSTELLATIONS_FILE_PATH));
    }

    @Override
    public String importConstellations() throws IOException {
        StringBuilder output = new StringBuilder();

        Arrays.stream(gson.fromJson(readConstellationsFromFile(), ConstellationSeedDto[].class))
                .filter(constellationSeedDto -> {
                    boolean isValid = validationUtil.isValid(constellationSeedDto)
                            && !isExisting(constellationSeedDto.getName());

                    output.append(isValid
                                    ? String.format("Successfully imported constellation %s - %s",
                                    constellationSeedDto.getName(), constellationSeedDto.getDescription())
                                    : "Invalid constellation")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(constellationSeedDto -> modelMapper.map(constellationSeedDto, Constellation.class))
                .forEach(constellationRepository::save);

        return output.toString().trim();
    }

    @Override
    public Constellation findById(Long constellationId) {
        return constellationRepository.findById(constellationId).orElse(null);
    }

    private boolean isExisting(String name) {
        return constellationRepository.existsByName(name);
    }
}
