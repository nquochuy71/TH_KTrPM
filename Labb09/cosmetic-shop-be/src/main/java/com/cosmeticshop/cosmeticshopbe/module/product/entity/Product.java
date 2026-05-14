package com.cosmeticshop.cosmeticshopbe.module.product.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String ingredients;

    @Column(columnDefinition = "TEXT")
    private String usageInstructions;

    @ElementCollection
    @CollectionTable(name = "product_suitable_skin_types", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "skin_type", length = 20)
    private List<String> suitableSkinTypes = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "product_skin_concerns", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "concern", length = 100)
    private List<String> skinConcerns = new ArrayList<>();

    @Column(nullable = false)
    private Double averageRating;

    @Column(nullable = false)
    private Integer totalReviews;

    @Column(nullable = false)
    private Integer totalSold;

    @Column(precision = 19, scale = 2)
    private BigDecimal minPrice;

    @Column(precision = 19, scale = 2)
    private BigDecimal maxPrice;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private Boolean isFeatured;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected Product() {
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        if (averageRating == null) {
            averageRating = 0D;
        }
        if (totalReviews == null) {
            totalReviews = 0;
        }
        if (totalSold == null) {
            totalSold = 0;
        }
        if (isActive == null) {
            isActive = true;
        }
        if (isFeatured == null) {
            isFeatured = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
