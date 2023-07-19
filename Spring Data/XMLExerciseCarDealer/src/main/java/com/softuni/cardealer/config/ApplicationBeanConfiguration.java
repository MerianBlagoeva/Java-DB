package com.softuni.cardealer.config;

import com.softuni.cardealer.model.dto.ex5.CustomerWithBoughtCarsDto;
import com.softuni.cardealer.model.entity.Customer;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<String, LocalDateTime> localDateConverter = new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(MappingContext<String, LocalDateTime> mappingContext) {
                return mappingContext.getSource() == null
                        ? LocalDateTime.now()
                        : LocalDateTime.parse(mappingContext.getSource(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
            }
        };


        //NoSuchFileException
//        Converter<Instant, String> instantStringConverter = new Converter<Instant, String>() {
//            @Override
//            public String convert(MappingContext<Instant, String> mappingContext) {
//                Instant instant = mappingContext.getSource();
//                return instant == null
//                        ? null
//                        : instant.toString();
//            }
//        };


        //java.lang.NoSuchMethodError: java.time.LocalDateTime.<init>()
//        @Override
//        public String convert (MappingContext < LocalDateTime, String > mappingContext){
//            LocalDateTime localDateTime = mappingContext.getSource();
//            return localDateTime == null
//                    ? null
//                    : localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
//        }


        modelMapper.addConverter(localDateConverter);

        modelMapper
                .typeMap(Customer.class, CustomerWithBoughtCarsDto.class)
                .addMappings(mapper -> mapper
                        .map(Customer::getName,
                                CustomerWithBoughtCarsDto::setFullName));

        return modelMapper;

    }
}
