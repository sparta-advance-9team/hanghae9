package com.sparta.hanghaestartproject.entity;

import com.sparta.hanghaestartproject.dto.CommentRequestDto;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Comment extends Timestamped {
     @Id
     @GeneratedValue (strategy = GenerationType.IDENTITY)
     private Long id;
     
     @Column (nullable = false)
     private String username;
     
     @Column (nullable = false)
     private String content;

     @Column
     private Long likeCommentNum;
     
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name="post_id")
     private Post post;
     
     @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
     private Set<LikeComment> likeCommentList = new HashSet<>();

     @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
     private List<ReComment> reCommentList = new ArrayList<>();


     public Comment(CommentRequestDto requestDto, String username) {
          this.username = username;
          this.content = requestDto.getContent();
     }
     
     public void updatePost(Post post){
          this.post = post;
     }
     
     public void update(CommentRequestDto requestDto) {
          this.content =  requestDto.getContent();
     }
     
     public Comment() {
     }
     
     public Long getId() {
          return id;
     }
     
     public String getUsername() {
          return username;
     }
     
     public String getContent() {
          return content;
     }
     
     public Post getPost() {
          return post;
     }
     
     public Set<LikeComment> getLikeCommentList() {
          return likeCommentList;
     }

     public void setLikeCommentNum(Long sum) {
          this.likeCommentNum = sum;
     }

     public Long getLikeCommentNum() {
          return this.likeCommentNum;
     }
     public List<ReComment> getReCommentList(){ return reCommentList;}

     public void addReComment(ReComment reComment){
          this.reCommentList.add(reComment);
          reComment.updateComment(this);

     }
}
