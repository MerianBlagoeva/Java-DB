package com.softuni.springdataautomappingobjectsexercise.dtoEx.config;

import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.dto.GameAddDto;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.entity.Game;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper
                .typeMap(GameAddDto.class, Game.class)
                .addMapping(GameAddDto)


        return modelMapper;
    }
}
