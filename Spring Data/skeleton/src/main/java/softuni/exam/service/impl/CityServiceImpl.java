package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.models.dto.seed.CityImportDto;
import softuni.exam.models.entity.City;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CityServiceImpl implements CityService {
    private static final String CITIES_FILE_NAME = "cities.json";
    private final CityRepository cityRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final CountryRepository countryRepository;

    public CityServiceImpl(CityRepository cityRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(GlobalConstants.RESOURCES_JSON_FILE_PATH + CITIES_FILE_NAME));
    }

    @Override
    public String importCities() throws IOException {
        StringBuilder out = new StringBuilder();

        String fileContent = this.readCitiesFileContent();

        CityImportDto[] cityImportDtos = gson
                .fromJson(fileContent, CityImportDto[].class);

        for (CityImportDto cityImportDto : cityImportDtos) {
            if (validationUtil.isValid(cityImportDto)) {
                City city = modelMapper.map(cityImportDto, City.class);

                if (cityRepository.findByCityName(city.getCityName()) == null) {

                    city.setCountry(countryRepository.findById(cityImportDto.getCountry()).orElse(null));
                    cityRepository.save(city);


                    out.append(String.format("Successfully imported city %s - %d",
                                    city.getCityName(), city.getPopulation()))
                            .append(System.lineSeparator());

                } else {
                    out.append("Invalid city").append(System.lineSeparator());
                }
            } else {
                out.append("Invalid city").append(System.lineSeparator());
            }
        }

        return out.toString().trim();
    }

    @Override
    public City findById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }
}
