package com.example.demo.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.example.demo.security.ApplicationUserPermission.*;

@Entity
public class ApplicationUser implements UserDetails {
    @Id
    private final String username;
    private final String password;
    private final boolean isAccountNonExpired;
    private final boolean isAccountNonLocked;
    private final boolean isCredentialsNonExpired;
    private final boolean isEnabled;
    private final boolean isAdmin;
    private final boolean isTrainee;

    public ApplicationUser() {
        this.username = "<no-name>";
        this.password = "";
        this.isAccountNonExpired = false;
        this.isAccountNonLocked = false;
        this.isCredentialsNonExpired = false;
        this.isEnabled = false;
        this.isAdmin = false;
        this.isTrainee = false;
    }

    public ApplicationUser(String username, String password,
                           boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired,
                           boolean isEnabled, boolean isAdmin, boolean isTrainee) {
        this.password = password;
        this.username = username;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
        this.isAdmin = isAdmin;
        this.isTrainee = isTrainee;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> result = new HashSet<>();
        result.add(new SimpleGrantedAuthority(COURSES_READ.getPermission()));
        result.add(new SimpleGrantedAuthority(STUDENT_READ.getPermission()));
        if (isAdmin) {
            result.add(new SimpleGrantedAuthority(COURSES_WRITE.getPermission()));
            result.add(new SimpleGrantedAuthority(STUDENT_WRITE.getPermission()));
        }
        return result;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
