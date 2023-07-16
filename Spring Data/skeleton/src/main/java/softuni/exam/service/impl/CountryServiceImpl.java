package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.GlobalConstants.RESOURCES_JSON_FILE_PATH;

@Service
public class CountryServiceImpl implements CountryService {
    private static final String COUNTRIES_FILE_NAME = "countries.json";

    private CountryRepository countryRepository;
    private final ValidationUtil validationUtil;

    private final ModelMapper modelMapper;

    private final Gson gson;

    public CountryServiceImpl(CountryRepository countryRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.countryRepository = countryRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
//        InputStream stream = this.getClass().getResourceAsStream("/files/json/countries.json");
//        byte[] bytes = stream.readAllBytes();
//        String jsonContent = new String(bytes);

        return Files.readString(Path.of(RESOURCES_JSON_FILE_PATH + COUNTRIES_FILE_NAME));
    }

    @Override
    public String importCountries() throws IOException {

        StringBuilder out = new StringBuilder();

        String fileContent = this.readCountriesFromFile();

        CountryImportDto[] countryImportDtos = gson.
                fromJson(fileContent, CountryImportDto[].class);

        for (CountryImportDto countryImportDto : countryImportDtos) {
            if (validationUtil.isValid(countryImportDto)) {
                Country country = modelMapper.map(countryImportDto, Country.class);

                if (countryRepository.findByCountryName(country.getCountryName()) == null) {

                    countryRepository.save(country);
                    out.append(String.format("Successfully imported country %s - %s",
                                    country.getCountryName(), country.getCurrency()))
                            .append(System.lineSeparator());
                }
            } else {
                out.append("Invalid country").append(System.lineSeparator());
            }
        }

        return out.toString().trim();
    }
}
