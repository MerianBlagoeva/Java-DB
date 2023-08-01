package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AstronomerSeedRootDto;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AstronomerServiceImpl implements AstronomerService {
    private static final String ASTRONOMERS_FILE_PATH = "src/main/resources/files/xml/astronomers.xml";
    private final AstronomerRepository astronomerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final StarService starService;

    public AstronomerServiceImpl(AstronomerRepository astronomerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, StarService starService) {
        this.astronomerRepository = astronomerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.starService = starService;
    }

    @Override
    public boolean areImported() {
        return astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return Files.readString(Path.of(ASTRONOMERS_FILE_PATH));
    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {
        StringBuilder output = new StringBuilder();

        xmlParser.fromFile(ASTRONOMERS_FILE_PATH, AstronomerSeedRootDto.class)
                .getAstronomers()
                .stream()
                .filter(astronomerSeedDto -> {
                    boolean isValid = validationUtil.isValid(astronomerSeedDto)
                            && !isExisting(astronomerSeedDto.getFirstName(), astronomerSeedDto.getLastName())
                            && starService.findById(astronomerSeedDto.getObservingStarId()) != null;

                    output.append(isValid
                                    ? String.format("Successfully imported astronomer %s %s - %.2f",
                                    astronomerSeedDto.getFirstName(), astronomerSeedDto.getLastName(),
                                    astronomerSeedDto.getAverageObservationHours())
                                    : "Invalid astronomer")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(astronomerSeedDto -> {
                    Astronomer astronomer = modelMapper.map(astronomerSeedDto, Astronomer.class);
                    astronomer.setObservingStar(starService.findById(astronomerSeedDto.getObservingStarId()));

                    return astronomer;
                })
                .forEach(astronomerRepository::save);

        return output.toString().trim();
    }

    private boolean isExisting(String firstName, String lastName) {
        return astronomerRepository.existsByFirstNameAndLastName(firstName, lastName);
    }
}
