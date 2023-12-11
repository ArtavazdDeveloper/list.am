package com.example.list_am.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.catalina.User;

import java.util.List;

@Entity
@Data
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String description;

    @ManyToOne
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    private String imgName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "item_hashtag",
            joinColumns =  @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    List<Hashtag> hashtagList;
}
