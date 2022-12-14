package com.sparta.hanghaestartproject.dto;

import com.sparta.hanghaestartproject.entity.Comment;
import com.sparta.hanghaestartproject.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {
     private Long id;
     private LocalDateTime createdAt;
     private LocalDateTime modifiedAt;
     private String title;
     private String username;
     private String content;
     
     private List<CommentResponseDto> comments = new ArrayList<>();

     private Long likePostNum;
     
     public PostResponseDto(Post entity){
          this.id = entity.getId();
          this.createdAt = entity.getCreatedAt();
          this.modifiedAt = entity.getModifiedAt();
          this.title = entity.getTitle();
          this.username = entity.getUsername();
          this.content = entity.getContent();
          this.comments = entity.getCommentList().stream()
               .map(CommentResponseDto::new).collect(Collectors.toList());
//          this.comments = entity.getCommentList();
          this.likePostNum = entity.getLikePostNum();
     }
}
