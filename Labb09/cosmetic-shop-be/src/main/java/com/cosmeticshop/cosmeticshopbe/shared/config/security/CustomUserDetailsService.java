package com.cosmeticshop.cosmeticshopbe.shared.config.security;

import com.cosmeticshop.cosmeticshopbe.module.auth.entity.Account;
import com.cosmeticshop.cosmeticshopbe.module.auth.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found with email: " + email));
        return new CustomUserDetails(account);
    }

    public UserDetails loadUserById(UUID id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found with id: " + id));
        return new CustomUserDetails(account);
    }
}
