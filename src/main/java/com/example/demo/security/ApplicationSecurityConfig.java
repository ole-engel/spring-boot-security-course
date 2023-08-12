package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.example.demo.security.ApplicationUserRole.*;

@Configuration
@EnableMethodSecurity
public class ApplicationSecurityConfig {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "index", "/css/*", "/js/*").permitAll()
                        .requestMatchers("/api/**").hasRole(STUDENT.name())
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/courses", true)
                )
                .rememberMe(remember -> remember
                        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                        .rememberMeParameter("remember-me")
                        .key("something_very_secured")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .logoutSuccessUrl("/login")
                )
        ;
        return http.build();
    }

    @Bean
    public UserDetailsManager users() {
        UserDetails annaSmith = User.builder()
                .username("anna.smith")
                .password(passwordEncoder.encode("password"))
                //.roles(STUDENT.name())
                .authorities(STUDENT.getGrantedAuthorities())
                .build();
        UserDetails lindaAdmin = User.builder()
                .username("linda.admin")
                .password(passwordEncoder.encode("password"))
                //.roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build();
        UserDetails tomAdmin = User.builder()
                .username("tom.admin")
                .password(passwordEncoder.encode("password"))
                //.roles(ADMIN_TRAINEE.name())
                .authorities(ADMIN_TRAINEE.getGrantedAuthorities())
                .build();
        return new InMemoryUserDetailsManager(annaSmith, lindaAdmin, tomAdmin);
    }
}
