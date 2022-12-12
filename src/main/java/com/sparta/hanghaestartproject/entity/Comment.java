package com.sparta.hanghaestartproject.entity;

import com.sparta.hanghaestartproject.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
     @Id
     @GeneratedValue (strategy = GenerationType.IDENTITY)
     private Long id;
     
     @Column (nullable = false)
     private String username;
     
     @Column (nullable = false)
     private String content;
     
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name="post_id")
     private Post post;
     
     @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
     private List<LikeComment> likeCommentList = new ArrayList<>();
     // !~~!~!
     
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
}
