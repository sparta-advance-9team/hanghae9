package com.sparta.hanghaestartproject.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@AllArgsConstructor
@Data
@Builder
@Getter
@Entity
@NoArgsConstructor
public class LikePost extends Timestamped{
     
     @Id
     @GeneratedValue (strategy = GenerationType.AUTO)
     private Long id;

     @Column(nullable = false)
     private boolean status;        //true : 따봉,  false : 따봉취소
//     @Column (nullable = false)
//     private String username;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "user_id", nullable = false)
     @OnDelete(action = OnDeleteAction.CASCADE)  // 유저가 지워지면 유저-게시글 간테이블 삭제
     private User user;
     
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name="post_id", nullable = false)
     private Post post;

     public LikePost(Post post, User user) {
          this.post = post;
          this.user = user;
          this.status = true;
     }

     public void unLikePost(Post post) {
          this.status = false;
//          post.setLiked(post.getLiked() -1);           //좋아요 개수 - 추후에 수정
     }
}
