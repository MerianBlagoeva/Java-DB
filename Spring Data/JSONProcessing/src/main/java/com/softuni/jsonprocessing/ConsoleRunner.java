package com.softuni.jsonprocessing;

import com.softuni.jsonprocessing.dto.StudentDTO;
import com.softuni.jsonprocessing.service.CourseServiceImpl;
import com.softuni.jsonprocessing.service.StudentServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private StudentServiceImpl studentService;
    private CourseServiceImpl courseService;

    public ConsoleRunner(StudentServiceImpl studentService, CourseServiceImpl courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @Override
    public void run(String... args) throws Exception {
        new StudentDTO(
                "First",
                20,
                false,
                List.of("Math", "Biology")
        );

        String studentJson = """
                {
                  "firstName": "first",
                  "age": 22,
                  "isGraduated": false,
                  "coursesTaken": [
                       "Math", "Biology"
                    ]
                 }
                """;

        studentService.create(studentJson);

        String courseJson = """
                {
                    "name": "Math",
                    "lengthInWeeks": 12
                }
                """;

        courseService.create(courseJson);
    }
}
