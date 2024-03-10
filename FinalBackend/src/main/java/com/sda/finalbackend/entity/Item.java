package com.sda.finalbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="tbl_items")
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private Double price;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private Category category;
}
