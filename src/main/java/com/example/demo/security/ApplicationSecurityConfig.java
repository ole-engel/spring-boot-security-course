package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.example.demo.security.ApplicationUserRole.*;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ApplicationSecurityConfig {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/", "index", "/css/*", "/js/*").permitAll()
                        .requestMatchers("/management/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                        .requestMatchers("/**").hasRole(STUDENT.name())
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsManager users() {
        UserDetails annaSmith = User.builder()
                .username("anna.smith")
                .password(passwordEncoder.encode("password"))
                .roles(STUDENT.name())
                .build();
        UserDetails lindaAdmin = User.builder()
                .username("linda.admin")
                .password(passwordEncoder.encode("password"))
                .roles(ADMIN.name())
                .build();
        UserDetails tomAdmin = User.builder()
                .username("tom.admin")
                .password(passwordEncoder.encode("password"))
                .roles(ADMINTRAINEE.name())
                .build();
        return new InMemoryUserDetailsManager(annaSmith, lindaAdmin, tomAdmin);
    }
}
