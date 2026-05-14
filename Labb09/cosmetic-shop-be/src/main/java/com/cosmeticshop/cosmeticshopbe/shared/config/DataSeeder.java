package com.cosmeticshop.cosmeticshopbe.shared.config;

import com.cosmeticshop.cosmeticshopbe.module.auth.entity.Account;
import com.cosmeticshop.cosmeticshopbe.module.auth.repository.AccountRepository;
import com.cosmeticshop.cosmeticshopbe.shared.enums.AccountRole;
import com.cosmeticshop.cosmeticshopbe.shared.enums.AccountStatus;
import com.cosmeticshop.cosmeticshopbe.shared.enums.AuthProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!prod") // Never runs in the 'prod' Spring profile
public class DataSeeder implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (accountRepository.count() == 0) {
            Account admin = Account.builder()
                    .email("admin@test.com")
                    .password(passwordEncoder.encode("admin123"))
                    .fullName("Site Administrator")
                    .phoneNumber("0123456789")
                    .role(AccountRole.ADMIN)
                    .status(AccountStatus.ACTIVE)
                    .provider(AuthProvider.LOCAL)
                    .emailVerified(true)
                    .build();

            Account employee = Account.builder()
                    .email("employee@test.com")
                    .password(passwordEncoder.encode("employee123"))
                    .fullName("Staff Employee")
                    .phoneNumber("0987654321")
                    .role(AccountRole.EMPLOYEE)
                    .status(AccountStatus.ACTIVE)
                    .provider(AuthProvider.LOCAL)
                    .emailVerified(true)
                    .build();

            Account customer = Account.builder()
                    .email("customer@test.com")
                    .password(passwordEncoder.encode("customer123"))
                    .fullName("Regular Customer")
                    .phoneNumber("0111222333")
                    .role(AccountRole.CUSTOMER)
                    .status(AccountStatus.ACTIVE)
                    .provider(AuthProvider.LOCAL)
                    .emailVerified(true)
                    .build();

            accountRepository.saveAll(List.of(admin, employee, customer));
            System.out.println("Data Seeder: Đã tạo thành công 3 tài khoản mẫu (admin, employee, customer).");
        }
    }
}
