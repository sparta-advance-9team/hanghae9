package com.sparta.hanghaestartproject.dto;

import com.sparta.hanghaestartproject.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommentResponseDto{
     private Long id;
     private LocalDateTime createdAt;
     private LocalDateTime modifiedAt;
     private String username;
     private String content;
     private Long likeCommentNum;
     private List<ReCommentResponseDto> recomments = new ArrayList<>();

     
     public CommentResponseDto(Comment comment){
          this.id = comment.getId();
          this.createdAt = comment.getCreatedAt();
          this.modifiedAt = comment.getModifiedAt();
          this.username = comment.getUsername();
          this.content = comment.getContent();

          this.likeCommentNum = comment.getLikeCommentNum();
     }

     public Long getLikeCommentNum(){
          return this.likeCommentNum;
     }
     public Long getId(){
          return this.id;
     }
     public LocalDateTime getCreatedAt(){
          return this.createdAt;
     }
     public LocalDateTime getModifiedAt(){
          return this.modifiedAt;
     }
     public String getUsername() {
          return username;
     }
     public String getContent() {
          return content;
     }

     public List<ReCommentResponseDto> getReComments(){return this.recomments;}
}
