package com.sparta.hanghaestartproject.entity;

import com.sparta.hanghaestartproject.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
     @Id
     @GeneratedValue (strategy = GenerationType.AUTO)
     private Long id;
     
     @Column (nullable = false)
     private String username;
     
     @Column (nullable = false)
     private String content;
     
     @ManyToOne
     @JoinColumn(name="article_id")
     private Article article;
     
     public Comment(CommentRequestDto requestDto, String username) {
          this.username = username;
          this.content = requestDto.getContent();
     }
     
     public void setArticle(Article article){
          this.article = article;
          article.getComments().add(this);
     }
}
