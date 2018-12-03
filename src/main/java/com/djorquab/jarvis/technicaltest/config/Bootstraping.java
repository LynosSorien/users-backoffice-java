package com.djorquab.jarvis.technicaltest.config;

import com.djorquab.jarvis.technicaltest.ApplicationConstants;
import com.djorquab.jarvis.technicaltest.manager.PropertyManager;
import com.djorquab.jarvis.technicaltest.model.Users;
import com.djorquab.jarvis.technicaltest.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
public class Bootstraping {
    private UsersRepository usersRepository;
    private PropertyManager propertyManager;

    public Bootstraping(UsersRepository usersRepository, PropertyManager propertyManager) {
        this.usersRepository = usersRepository;
        this.propertyManager = propertyManager;
        initBootstrapping();
    }

    private void initBootstrapping() {
        log.info("Initializing bootstraping....");
        log.info("Creating admin account...");
        Users adminUser = Users.builder().username(propertyManager.getAdminUsername())
                .password(new BCryptPasswordEncoder().encode(propertyManager.getAdminPassword()))
                .email(propertyManager.getAdminEmail())
                .decodedPassword(propertyManager.getAdminPassword())
                .roles(ApplicationConstants.ADMIN_ROLE)
                .build();
        usersRepository.save(adminUser);
        log.info("Bootstraping is now ended!");
    }
}
