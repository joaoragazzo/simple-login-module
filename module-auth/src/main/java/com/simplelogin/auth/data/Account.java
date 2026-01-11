package com.simplelogin.auth.data;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users", schema = "auth")
public class Account {
    
    @Id
    @UuidGenerator
    private UUID id;
}
