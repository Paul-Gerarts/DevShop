package be.syntra.devshop.DevshopBack.config;


import be.syntra.devshop.DevshopBack.services.DTOMapper;
import be.syntra.devshop.DevshopBack.services.DTOMapperService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public DTOMapperService dtoMapperService() {
        return new DTOMapper();
    }
}
