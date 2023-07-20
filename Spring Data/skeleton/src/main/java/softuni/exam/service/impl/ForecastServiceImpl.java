package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.seed.ForecastImportDto;
import softuni.exam.models.dto.seed.ForecastImportRootDto;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.GlobalConstants.RESOURCES_XML_FILE_PATH;

@Service
public class ForecastServiceImpl implements ForecastService {
    private static final String FORECASTS_FILE_NAME = "forecasts.xml";

    private final ForecastRepository forecastRepository;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public ForecastServiceImpl(ForecastRepository forecastRepository, CityRepository cityRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.forecastRepository = forecastRepository;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Path.of(RESOURCES_XML_FILE_PATH + FORECASTS_FILE_NAME));

    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        StringBuilder out = new StringBuilder();


        ForecastImportRootDto forecastImportRootDto = xmlParser
                .fromFile(RESOURCES_XML_FILE_PATH + FORECASTS_FILE_NAME, ForecastImportRootDto.class);

        for (ForecastImportDto forecastImportDto : forecastImportRootDto.getForecasts()) {
            if (validationUtil.isValid(forecastImportDto)) {
                Forecast forecast = modelMapper.map(forecastImportDto, Forecast.class);

                if (forecastRepository.findByDayOfWeek(forecastImportDto.getDayOfWeek()) == null) {
                    forecast.setCity(cityRepository.findById(forecastImportDto.getCity()).orElse(null));
                    forecastRepository.save(forecast);
                    out.append(String.format("Successfully import forecast %s - %.2f", forecast.getDayOfWeek(), forecast.getMaxTemperature())).append(System.lineSeparator());
                } else {
                    out.append("Invalid forecast").append(System.lineSeparator());
                }
            }
            else {
                out.append("Invalid forecast").append(System.lineSeparator());
            }
        }
        return out.toString().trim();
    }

    @Override
    public String exportForecasts() {
        return null;
    }
}
