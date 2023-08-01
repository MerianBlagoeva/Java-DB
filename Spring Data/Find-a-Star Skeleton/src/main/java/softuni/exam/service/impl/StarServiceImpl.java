package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.StarSeedDto;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.enums.StarType;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class StarServiceImpl implements StarService {
    private static final String STARS_FILE_PATH = "src/main/resources/files/json/stars.json";
    private final StarRepository starRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final ConstellationService constellationService;

    public StarServiceImpl(StarRepository starRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, ConstellationService constellationService) {
        this.starRepository = starRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.constellationService = constellationService;
    }

    @Override
    public boolean areImported() {
        return starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return Files.readString(Path.of(STARS_FILE_PATH));
    }

    @Override
    public String importStars() throws IOException {
        StringBuilder output = new StringBuilder();

        Arrays.stream(gson.fromJson(readStarsFileContent(), StarSeedDto[].class))
                .filter(starSeedDto -> {
                    boolean isValid = validationUtil.isValid(starSeedDto)
                            && !isExisting(starSeedDto.getName());
                    output.append(isValid
                                    ? String.format("Successfully imported star %s - %.2f light years",
                                    starSeedDto.getName(), starSeedDto.getLightYears())
                                    : "Invalid star")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(starSeedDto -> {
                    Star star = modelMapper.map(starSeedDto, Star.class);
                    star.setConstellation(constellationService.findById(starSeedDto.getConstellation()));

                    return star;
                })
                .forEach(starRepository::save);

        return output.toString().trim();
    }

    private boolean isExisting(String name) {
        return starRepository.existsByName(name);
    }

    @Override
    public Star findById(Long id) {
        return starRepository.findById(id).orElse(null);
    }

    @Override
    public String exportStars() {
        StringBuilder output = new StringBuilder();

        starRepository.findAllByStarTypeAndAstronomers_EmptyOrderByLightYears(StarType.RED_GIANT)
                .forEach(star -> output.append(String.format("Star: %s\n" +
                                "   *Distance: %.2f light years\n" +
                                "   **Description: %s\n" +
                                "   ***Constellation: %s\n",
                        star.getName(),
                        star.getLightYears(),
                        star.getDescription(),
                        star.getConstellation().getName())));


        return output.toString().trim();
    }
}
