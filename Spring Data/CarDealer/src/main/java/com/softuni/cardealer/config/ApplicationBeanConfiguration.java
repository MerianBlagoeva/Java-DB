package com.softuni.cardealer.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softuni.cardealer.model.dto.CustomerWithBoughtCarsDto;
import com.softuni.cardealer.model.entity.Customer;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<String, LocalDate> localDateConverter = new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(MappingContext<String, LocalDate> mappingContext) {
                return mappingContext.getSource() == null
                        ? LocalDate.now()
                        : LocalDate.parse(mappingContext.getSource(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
            }
        };

        modelMapper.addConverter(localDateConverter);

        modelMapper
                .typeMap(Customer.class, CustomerWithBoughtCarsDto.class)
                .addMappings(mapper -> mapper
                        .map(Customer::getName,
                                CustomerWithBoughtCarsDto::setFullName));

        return modelMapper;

    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }
}
