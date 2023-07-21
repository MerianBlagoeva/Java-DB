package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TaskSeedRootDto;
import softuni.exam.models.entity.Task;
import softuni.exam.models.entity.enums.CarType;
import softuni.exam.repository.TasksRepository;
import softuni.exam.service.CarsService;
import softuni.exam.service.MechanicsService;
import softuni.exam.service.PartsService;
import softuni.exam.service.TasksService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

@Service
public class TasksServiceImpl implements TasksService {
    private static String TASKS_FILE_PATH = "src/main/resources/files/xml/tasks.xml";
    private final TasksRepository tasksRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final MechanicsService mechanicsService;
    private final CarsService carsService;
    private final PartsService partsService;

    public TasksServiceImpl(TasksRepository tasksRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, MechanicsService mechanicsService, CarsService carsService, PartsService partsService) {
        this.tasksRepository = tasksRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.mechanicsService = mechanicsService;
        this.carsService = carsService;
        this.partsService = partsService;
    }

    @Override
    public boolean areImported() {
        return tasksRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(Path.of(TASKS_FILE_PATH));
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        StringBuilder output = new StringBuilder();

        xmlParser.fromFile(TASKS_FILE_PATH, TaskSeedRootDto.class)
                .getTasks()
                .stream()
                .filter(taskSeedDto -> {
                    boolean isForImport = validationUtil.isValid(taskSeedDto)
                            && mechanicsService.findMechanicByName(taskSeedDto.getMechanic().getFirstName()) != null
                            && carsService.findById(taskSeedDto.getCar().getId()) != null;

                    output.append(isForImport ? String.format(Locale.US, "Successfully imported task %.2f",
                                    taskSeedDto.getPrice())
                                    : "Invalid task")
                            .append(System.lineSeparator());

                    return isForImport;
                })
                .map(taskSeedDto -> {
                    Task task = modelMapper.map(taskSeedDto, Task.class);

                    task.setCar(carsService.findById(taskSeedDto.getCar().getId()));
                    task.setMechanic(mechanicsService.findMechanicByName(taskSeedDto.getMechanic().getFirstName()));
                    task.setPart(partsService.findById(taskSeedDto.getPart().getId()));

                    return task;
                })
                .forEach(tasksRepository::save);

        return output.toString().trim();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        StringBuilder output = new StringBuilder();
        List<Task> allByCarCarTypeOrderByPriceDesc = tasksRepository
                .findAllByCarCarTypeOrderByPriceDesc(CarType.coupe);

        allByCarCarTypeOrderByPriceDesc
                .forEach(task -> output.append(String.format("Car %s %s with %dkm\n" +
                        "-Mechanic: %s %s - task №%d:\n" +
                        " --Engine: %.1f\n" +
                        "---Price: %.2f$\n",
                        task.getCar().getCarMake(), task.getCar().getCarModel(), task.getCar().getKilometers(),
                        task.getMechanic().getFirstName(), task.getMechanic().getLastName(), task.getId(),
                        task.getCar().getEngine(),
                        task.getPrice())));

        return output.toString().trim();
    }
}
