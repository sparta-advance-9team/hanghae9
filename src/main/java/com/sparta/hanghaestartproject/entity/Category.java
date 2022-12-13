package com.sparta.hanghaestartproject.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category;

    @ManyToMany(mappedBy = "categories")
    private List<Post> posts = new ArrayList<>();
}
