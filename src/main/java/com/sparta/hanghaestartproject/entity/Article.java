package com.sparta.hanghaestartproject.entity;

import com.sparta.hanghaestartproject.dto.ArticleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Article extends Timestamped{
     @Id
     @GeneratedValue (strategy = GenerationType.AUTO)
     private Long id;
     
     @Column (nullable = false)
     private String title;
     
     @Column (nullable = false)
     private String username;
     
     @Column (nullable = false)
     private String content;
     
     public Article(ArticleRequestDto requestDto, String username){
          this.title = requestDto.getTitle();
          this.content = requestDto.getContent();
          this.username = username;
     }
     
     public void update(ArticleRequestDto requestDto) {
          this.title = requestDto.getTitle();
          this.content = requestDto.getContent();
     }
}
