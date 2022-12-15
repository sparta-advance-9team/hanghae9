package com.sparta.hanghaestartproject.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
//@NoArgsConstructor
public class LikePost extends Timestamped{

     
     @Id
     @GeneratedValue (strategy = GenerationType.AUTO)
     private Long id;

     @Column(nullable = false)
     private boolean status;        //true : 따봉,  false : 따봉취소

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "user_id", nullable = false)
     @OnDelete(action = OnDeleteAction.CASCADE)  // 유저가 지워지면 유저-게시글간 row 삭제  /delete로 지우면 fk로 엮여있어서 안됨 / db에서 직접처리 따라서 삭제쿼리도안나감 서버비용줄일듯?
     private User user;
     
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name="post_id", nullable = false)
     private Post post;

     public LikePost(Post post, User user) {
          this.post = post;
          this.user = user;
          this.status = true;
     }

     public LikePost() {
          // Args가 No인 기본Constructor 생성
     }

     public void unLikePost(Post post) {
          this.status = false;
     }
}
