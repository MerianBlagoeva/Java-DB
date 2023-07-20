package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.seed.ForecastImportRootDto;
import softuni.exam.models.entity.DayOfWeek;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.CityService;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static softuni.exam.constants.GlobalConstants.RESOURCES_XML_FILE_PATH;

@Service
public class ForecastServiceImpl implements ForecastService {
    private static final String FORECASTS_FILE_NAME = "forecasts.xml";
    private final ForecastRepository forecastRepository;
    private final CityService cityService;

    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public ForecastServiceImpl(ForecastRepository forecastRepository, CityService cityService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.forecastRepository = forecastRepository;
        this.cityService = cityService;
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


        xmlParser
                .fromFile(RESOURCES_XML_FILE_PATH + FORECASTS_FILE_NAME, ForecastImportRootDto.class)
                .getForecasts()
                .stream()
                .filter(forecastImportDto -> {
                    boolean isValid = validationUtil.isValid(forecastImportDto);

                    List<Forecast> forecastsWithSameDayOfWeek = forecastRepository.findByDayOfWeek(forecastImportDto.getDayOfWeek());

                    boolean isExisting = false;

                    if (!forecastsWithSameDayOfWeek.isEmpty()) {
                        for (Forecast forecast : forecastsWithSameDayOfWeek) {
                            if (forecast.getCity().getId() == forecastImportDto.getCity()) {
                                isExisting = true;
                                break;
                            }
                        }
                    }


                    out.append(isValid && !isExisting ? String.format("Successfully import forecast - %s - %.2f",
                            forecastImportDto.getDayOfWeek(), forecastImportDto.getMaxTemperature())
                                    : "Invalid forecast")
                            .append(System.lineSeparator());

                    return isValid && !isExisting;
                })
                .map(forecastImportDto -> {
                    Forecast forecast = modelMapper.map(forecastImportDto, Forecast.class);
                    forecast.setCity(cityService.findById(forecastImportDto.getCity()));

                    return forecast;
                })
                .forEach(forecastRepository::save);


        return out.toString().trim();
    }

    @Override
    public String exportForecasts() {

        return forecastRepository
                .findAllByDayOfWeekAndCityPopulationLessThanOrderByMaxTemperatureDescIdAsc(DayOfWeek.SUNDAY, 150000)
                .stream()
                .map(forecast -> String.format(
                        "City: %s:\n" +
                                "-min temperature: %.2f\n" +
                                "--max temperature: %.2f\n" +
                                "---sunrise: %s\n" +
                                "----sunset: %s\n",
                        forecast.getCity().getCityName(),
                        forecast.getMinTemperature(),
                        forecast.getMaxTemperature(),
                        forecast.getSunrise(),
                        forecast.getSunset()
                ))
                .collect(Collectors.joining("\n"));
    }
}
