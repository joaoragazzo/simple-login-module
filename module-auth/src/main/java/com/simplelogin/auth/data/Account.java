package com.simplelogin.auth.data;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", schema = "auth")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Account implements UserDetails {
    
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, unique = true, updatable = false)
    private String email;

    @Column(nullable = false)
    private String hashedPassword;

    /* Login rate controller */
    private Integer loginAttempts;
    private Instant isLockedUntil;

    /* Activation data */
    @Column(nullable = false)
    private Boolean isEnabled;

    @Column(nullable = false)
    private String activationCode;

    @Column(nullable = false)
    private Instant activationCodeExpireAt;

    /* Logs data */
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;
    private Instant lastLoginAt;
    private String lastSuccessfulLoginIpAddress;


    @PreUpdate
    private void onUpdate() {
        updatedAt = Instant.now();
    }

    @PrePersist
    private void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;

        loginAttempts = 0;

        isEnabled = false;
        this.activationCode = UUID.randomUUID().toString();
        this.activationCodeExpireAt = now.plusSeconds(3600);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.hashedPassword;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        throw new UnsupportedOperationException("Unimplemented method 'isAccountNonExpired'");
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isLockedUntil == null || Instant.now().isAfter(this.isLockedUntil);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
