package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("postgres")
public class PostgresApplicationUserDaoService extends SimpleJpaRepository<ApplicationUser, String> implements ApplicationUserDao {
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PostgresApplicationUserDaoService(PasswordEncoder passwordEncoder, JpaContext context) {
        super(ApplicationUser.class, context.getEntityManagerByManagedType(ApplicationUser.class));
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        Optional<ApplicationUser> result = findById(username);
        if (result.isPresent()) {
            String encryptedPassword = passwordEncoder.encode(result.get().getPassword());
            int STOP = 0;
        }
        return result;
    }
}
