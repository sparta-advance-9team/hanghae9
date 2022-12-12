package com.sparta.hanghaestartproject.dto;

import lombok.Getter;
import lombok.Setter;

public class CommentRequestDto {
     private String content;
     
     public void setContent(String content) {
          this.content = content;
     }
     public String getContent(){
          return this.content;
     }
}
