package com.sparta.hanghaestartproject.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
//@NoArgsConstructor
public class LikeComment extends Timestamped{
     
     @Id
     @GeneratedValue (strategy = GenerationType.AUTO)
     private Long id;

     @Column(nullable = false)
     private boolean status;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "user_id", nullable = false)
     @OnDelete(action = OnDeleteAction.CASCADE)   // 유저가 지워지면 유저-댓글 간테이블 삭제
     private User user;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name="comment_id", nullable = false)
     private Comment comment;

     public LikeComment(Comment comment, User user) {
          this.comment = comment;
          this.user = user;
          this.status = true;
     }

     public LikeComment() {
          // Args가 No인 기본Constructor 생성
     }

     public void unLikeComment(Comment comment) {
          this.status = false;
//          post.setLiked(post.getLiked() -1);           //좋아요 개수 - 추후에 수정
     }
}
