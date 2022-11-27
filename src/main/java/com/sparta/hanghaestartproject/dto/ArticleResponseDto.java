package com.sparta.hanghaestartproject.dto;

import com.sparta.hanghaestartproject.entity.Article;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleResponseDto {
     private Long id;
     private LocalDateTime createdAt;
     private LocalDateTime modifiedAt;
     private String title;
     private String username;
     private String content;
     
     public ArticleResponseDto(Article entity){
          this.id = entity.getId();
          this.createdAt = entity.getCreatedAt();
          this.modifiedAt = entity.getModifiedAt();
          this.title = entity.getTitle();
          this.username = entity.getUsername();
          this.content = entity.getContent();
     }
}
