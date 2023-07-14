package com.softuni.jsonexercise.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softuni.jsonexercise.model.dto.CategoryInfoDto;
import com.softuni.jsonexercise.model.entity.Category;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper
                .typeMap(Category.class, CategoryInfoDto.class)
                .addMappings(mapper ->
                        mapper.map(Category::getName, CategoryInfoDto::setCategory));

        return modelMapper;
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }
}
