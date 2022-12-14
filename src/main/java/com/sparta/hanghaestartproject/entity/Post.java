package com.sparta.hanghaestartproject.entity;

import com.sparta.hanghaestartproject.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Post extends Timestamped{
     @Id
     @GeneratedValue (strategy = GenerationType.IDENTITY)
     private Long id;
     
     @Column (nullable = false)
     private String title;
     
     @Column (nullable = false)
     private String username;

     @Column (nullable = false)
     private String content;

     @OneToMany(mappedBy = "post")
     private List<Category> categories = new ArrayList<>();

     @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
     private List<Comment> commentList = new ArrayList<>();
     
     @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
     private List<LikePost> likePostList = new ArrayList<>();
     
     public Post(PostRequestDto requestDto, String username){
          this.title = requestDto.getTitle();
          this.content = requestDto.getContent();
          this.username = username;
//          this.categories =  requestDto.getCategories();
     }

     public Post(PostRequestDto requestDto, String username, List<CategoryEnum> categoryEnums){
          this.title = requestDto.getTitle();
          this.content = requestDto.getContent();
          this.username = username;

     }
     
     public void update(PostRequestDto requestDto) {
          this.title = requestDto.getTitle();
          this.content = requestDto.getContent();
          
     }
     
     public void setCommentList(List<Comment> commentList){
          this.commentList = commentList;
     }
     
     public void addComment(Comment comment){
          this.commentList.add(comment);
          comment.updatePost(this);
     }
}
