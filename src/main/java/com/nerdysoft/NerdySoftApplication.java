package com.nerdysoft;

import com.nerdysoft.model.Role;
import com.nerdysoft.model.User;
import com.nerdysoft.model.UserStatus;
import com.nerdysoft.repository.RoleRepository;
import com.nerdysoft.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
public class NerdySoftApplication {

	public static void main(String[] args) {
		SpringApplication.run(NerdySoftApplication.class, args);
	}

	@Bean
    public CommandLineRunner commandLineRunner(RoleRepository roleRepository,
											   UserRepository userRepository,
											   PasswordEncoder passwordEncoder) {
        return args -> {
			roleRepository.save(Role.builder().role("USER").build());
			roleRepository.save(Role.builder().role("ADMIN").build());
			userRepository.save(User.builder()
					.email("nazar.gladish@gmail.com")
					.firstName("Nazar")
					.lastName("Hladysh")
					.password(passwordEncoder.encode("nazar12345"))
					.status(UserStatus.ACTIVATED)
					.roles(new HashSet<>(Collections.singletonList(roleRepository.findByRole("USER"))))
					.build()
			);
		};
    }
}
