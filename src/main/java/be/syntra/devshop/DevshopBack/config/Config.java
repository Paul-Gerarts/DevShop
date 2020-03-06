package be.syntra.devshop.DevshopBack.config;


import be.syntra.devshop.DevshopBack.services.mappers.DTOToEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public DTOToEntityMapper dtoToEntityMapperService() {
        return new DTOToEntityMapper();
    }
}
