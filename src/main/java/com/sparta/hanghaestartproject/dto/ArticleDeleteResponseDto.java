package com.sparta.hanghaestartproject.dto;

import lombok.Getter;

@Getter
public class ArticleDeleteResponseDto {
     private Boolean success;
     
     public ArticleDeleteResponseDto(Boolean result){
          this.success = result;
     }
}
