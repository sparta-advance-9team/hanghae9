package com.sparta.hanghaestartproject.entity;

import com.sparta.hanghaestartproject.dto.ArticleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Article extends Timestamped{
     @Id
     @GeneratedValue (strategy = GenerationType.AUTO)
     private Long id;
     
     @Column (nullable = false)
     private String title;
     
     @Column (nullable = false)
     private String username;
     
     @Column (nullable = false)
     private String content;
     
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name="user_id")
     private User user;
     
     @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
     private List<Comment> commentList = new ArrayList<>();
     
     public Article(ArticleRequestDto requestDto, String username){
          this.title = requestDto.getTitle();
          this.content = requestDto.getContent();
          this.username = username;
     }
     
     public void update(ArticleRequestDto requestDto) {
          this.title = requestDto.getTitle();
          this.content = requestDto.getContent();
     }
     
     public void setUser(User user) {
          this.user = user;
          user.getArticles().add(this);
     }
     
     public void addComment(Comment comment){
          this.commentList.add(comment);
          comment.updateArticle(this);
     }
}
