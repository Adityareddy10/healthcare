package com.healthcare.backend;

import com.healthcare.backend.entity.User;
import com.healthcare.backend.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class BackendApplication {

	private static final Logger log = LoggerFactory.getLogger(BackendApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner seedAdmin(UserRepository users, PasswordEncoder encoder) {
		return args -> {
			if (users.findByUsername("admin").isEmpty()) {
				User admin = new User();
				admin.setUsername("admin");
				admin.setEmail("admin@example.com");
				admin.setPassword(encoder.encode("admin123"));
				admin.setRole("ADMIN");
				admin.setActive(true);
				users.save(admin);
				log.info("Seeded default admin user");
			} else {
				log.info("Admin user already present; skipping seed");
			}
		};
	}

}
