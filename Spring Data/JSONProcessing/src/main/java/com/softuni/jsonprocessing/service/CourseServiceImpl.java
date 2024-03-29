package com.softuni.jsonprocessing.service;

import com.google.gson.Gson;
import com.softuni.jsonprocessing.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl {

    private final Gson gson;

    public CourseServiceImpl(@Qualifier("withExpose") Gson gson) {
        this.gson = gson;
    }

    public void create(String createJson) {
        CourseDTO courseDTO = this.gson.fromJson(createJson, CourseDTO.class);

        System.out.println("Created -> " + courseDTO);
    }
}
