package com.djorquab.jarvis.technicaltest.config;

import com.djorquab.jarvis.technicaltest.manager.PropertyManager;
import com.djorquab.jarvis.technicaltest.mappers.UsersMapper;
import com.djorquab.jarvis.technicaltest.repository.UsersRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Autowired
    private UsersRepository usersRepositoy;

    @Autowired
    private PropertyManager propertyManager;

    @Bean
    public UsersMapper usersMapper() {
        return Mappers.getMapper(UsersMapper.class);
    }

    @Bean
    public Bootstraping bootstraping() {
        return new Bootstraping(usersRepositoy, propertyManager);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(1000*60*3)
                .setReadTimeout(1000*60*3)
                .build();
    }
}
