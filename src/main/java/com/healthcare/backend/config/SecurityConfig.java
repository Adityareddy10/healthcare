package com.healthcare.backend.config;

import com.healthcare.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig implements WebMvcConfigurer {

    @Value("${CORS_ALLOWED_ORIGINS:http://localhost:3000,http://localhost:4200}")
    private String corsAllowedOrigins;

    // 🔐 Password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 👤 Load user from DB correctly (NO ROLE_ MAGIC)
    @Bean
    public UserDetailsService userDetailsService(UserRepository users) {
        return username -> users.findByUsername(username)
            .map(u -> User.builder()
                .username(u.getUsername())
                .password(u.getPassword()) // must already be BCrypt
                .authorities(List.of(
                    new SimpleGrantedAuthority("ROLE_" + u.getRole())
                ))
                .disabled(!Boolean.TRUE.equals(u.getActive()))
                .build()
            )
            .orElseThrow(() ->
                new UsernameNotFoundException("User not found: " + username)
            );
    }

    // 🔒 Security rules
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/health").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
            )
            .httpBasic(basic -> {});

        return http.build();
    }

    // 🌐 CORS config
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = corsAllowedOrigins.split(",");
        for (int i = 0; i < origins.length; i++) {
            origins[i] = origins[i].trim();
        }
        
        registry.addMapping("/api/**")
            .allowedOrigins(origins)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(false)
            .maxAge(3600);
    }
}
