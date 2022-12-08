package com.sparta.hanghaestartproject.dto;

import com.sparta.hanghaestartproject.entity.Article;
import com.sparta.hanghaestartproject.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ArticleResponseDto{
     private Long id;
     private LocalDateTime createdAt;
     private LocalDateTime modifiedAt;
     private String title;
     private String username;
     private String content;
     
     private List<CommentResponseDto> comments = new ArrayList<>();
     
     public ArticleResponseDto(Article entity){
          this.id = entity.getId();
          this.createdAt = entity.getCreatedAt();
          this.modifiedAt = entity.getModifiedAt();
          this.title = entity.getTitle();
          this.username = entity.getUsername();
          this.content = entity.getContent();
          this.comments = entity.getCommentList().stream()
               .map(CommentResponseDto::new).collect(Collectors.toList());
     }
}
