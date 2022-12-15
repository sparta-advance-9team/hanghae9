package com.sparta.hanghaestartproject.entity;

import com.sparta.hanghaestartproject.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Getter
@Entity
//@NoArgsConstructor
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

     @Column(nullable = true)
     private Long likePostNum;


     public Post(){
     }

     public Long getId() {
          return this.id;
     }


     public String getTitle(){
          return this.title;
     }

     public String getUsername(){
          return this.username;
     }

     public String getContent(){
          return this.content;
     }

     public List<Comment> getCommentList(){
          return commentList;
     }

     public List<LikePost> getLikePostList(){
          return likePostList;
     }

     
//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name="user_id")
//     private User user;
     @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
     private List<Comment> commentList = new ArrayList<>();
     
     @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
     private List<LikePost> likePostList = new ArrayList<>();
     
     public Post(PostRequestDto requestDto, String username){
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

     public void setLikePostNum(Long sum) {// setLikePostNum 인자값을 뭘넣어야할까?
          this.likePostNum = sum;
     }
     
     public Long getLikePostNum() {
          return likePostNum;
     }
}
