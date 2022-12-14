package com.sparta.hanghaestartproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private CategoryEnum categoryEnum;

    //category(many 갑) post(one 을)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    public Category() {
    }
    
    public Category(CategoryEnum categoryEnum, Post post){
        this.categoryEnum = categoryEnum;
        this.post = post;
    }
}
