package com.cosmeticshop.cosmeticshopbe.module.customer.entity;

import com.cosmeticshop.cosmeticshopbe.module.auth.entity.Account;
import com.cosmeticshop.cosmeticshopbe.shared.enums.Gender;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "id")
    private Account account;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    @Column(nullable = false)
    private Integer loyaltyPoints;

    @Column(length = 20)
    private String skinType;

    @ElementCollection
    @CollectionTable(name = "customer_skin_concerns", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "concern", length = 100)
    private List<String> skinConcerns = new ArrayList<>();

    protected Customer() {
    }

    @PrePersist
    protected void onCreate() {
        if (loyaltyPoints == null) {
            loyaltyPoints = 0;
        }
    }
}
