package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PartSeedDto;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartsRepository;
import softuni.exam.service.PartsService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class PartsServiceImpl implements PartsService {
    private static final String PARTS_FILE_PATH = "src/main/resources/files/json/parts.json";
    private final PartsRepository partsRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public PartsServiceImpl(PartsRepository partsRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.partsRepository = partsRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return partsRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(Path.of(PARTS_FILE_PATH));
    }

    @Override
    public String importParts() throws IOException {
        StringBuilder output = new StringBuilder();

        Arrays.stream(gson.fromJson(readPartsFileContent(), PartSeedDto[].class))
                .filter(partSeedDto -> {
                    boolean isValid = validationUtil.isValid(partSeedDto);
                    boolean isExisting = partsRepository.findByPartName(partSeedDto.getPartName()).isPresent();

                    boolean isForImport = isValid && !isExisting;

                    output.append(isForImport ? String.format("Successfully imported part %s - %s", //??? How can the price be passes as %s
                            partSeedDto.getPartName(), partSeedDto.getPrice())
                            : "Invalid part")
                            .append(System.lineSeparator());

                    return isForImport;
                })
                .map(partSeedDto -> modelMapper.map(partSeedDto, Part.class))
                .forEach(partsRepository::save);

        return output.toString().trim();
    }

    @Override
    public Part findById(Long id) {

        return partsRepository.findById(id).orElse(null);
    }
}
