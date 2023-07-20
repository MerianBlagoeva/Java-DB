package softuni.exam.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.exam.models.dto.seed.CityImportDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
//
//        modelMapper.
//                typeMap(CityImportDto.class, City.class)
//                .addMappings(mapper ->
//                        mapper.map(CityImportDto::getCountry, Country::setId));

        return modelMapper;
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .create();
    }
}
