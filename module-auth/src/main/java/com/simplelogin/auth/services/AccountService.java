package com.simplelogin.auth.services;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.simplelogin.auth.data.Account;
import com.simplelogin.auth.events.AccountCreatedEvent;
import com.simplelogin.auth.repository.AccountRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Account registerNewAccount(String email, String password) {
        String hashedPassword = passwordEncoder.encode(password);
        Account newAccount = Account.builder()
                .email(email)
                .hashedPassword(hashedPassword)
                .build();

        Account account = accountRepository.save(newAccount);
        eventPublisher.publishEvent(new AccountCreatedEvent(account.getEmail(), account.getActivationToken()));
        return account;
    }
}
