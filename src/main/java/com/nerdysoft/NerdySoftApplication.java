package com.nerdysoft;

import com.nerdysoft.config.JwtConfig;
import com.nerdysoft.model.Role;
import com.nerdysoft.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class NerdySoftApplication {

    public static void main(String[] args) {
        SpringApplication.run(NerdySoftApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
        return args -> {
            roleRepository.save(Role.builder().role("USER").build());
            roleRepository.save(Role.builder().role("ADMIN").build());

        };
    }
}
