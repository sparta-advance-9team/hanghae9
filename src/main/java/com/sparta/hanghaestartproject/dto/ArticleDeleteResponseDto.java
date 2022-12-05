package com.sparta.hanghaestartproject.dto;

import lombok.Getter;

@Getter
public class ArticleDeleteResponseDto {
     private String msg = "게시글 삭제 성공";
     private int statusCode = 200;
}
