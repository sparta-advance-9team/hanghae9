package com.sparta.hanghaestartproject.dto;

import com.sparta.hanghaestartproject.entity.Article;
import com.sparta.hanghaestartproject.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto implements ResonseImpl{
     private Long id;
     private LocalDateTime createdAt;
     private LocalDateTime modifiedAt;
     private String username;
     private String content;
     
     
     public CommentResponseDto(Comment comment){
          this.id = comment.getId();
          this.createdAt = comment.getCreatedAt();
          this.modifiedAt = comment.getModifiedAt();
          this.username = comment.getUsername();
          this.content = comment.getContent();
     }
}
