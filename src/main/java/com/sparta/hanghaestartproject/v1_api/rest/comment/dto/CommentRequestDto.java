package com.sparta.hanghaestartproject.v1_api.rest.comment.dto;

public class CommentRequestDto {
     private String content;
     
     public void setContent(String content) {
          this.content = content;
     }
     public String getContent(){
          return this.content;
     }
}
