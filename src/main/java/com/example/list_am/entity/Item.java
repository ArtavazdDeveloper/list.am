package com.example.list_am.entity;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    private String description;

    @ManyToOne
    private Category category;
}
