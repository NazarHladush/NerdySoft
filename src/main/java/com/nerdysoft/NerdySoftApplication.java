package com.nerdysoft;

import com.nerdysoft.config.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class NerdySoftApplication {

    public static void main(String[] args) {
        SpringApplication.run(NerdySoftApplication.class, args);
    }


}
