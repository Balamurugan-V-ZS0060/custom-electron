package com.zuci.electron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
//@EnableWebSecurity(debug = true)
//@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class ElectronApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ElectronApplication.class, args);
    }
}