package com.sparta.hanghaestartproject.v2_api.rest.comment.model;

import com.sparta.hanghaestartproject.v1_api.rest.comment.dto.ReCommentRequestDto;
import com.sparta.hanghaestartproject.config.model.Timestamped;
import com.sparta.hanghaestartproject.v1_api.rest.comment.model.Comment;

import javax.persistence.*;

@Entity
public class ReComment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private com.sparta.hanghaestartproject.v1_api.rest.comment.model.Comment comment;


    public ReComment(){

    }

    public ReComment(ReCommentRequestDto requestDto, String usename){
        this.username = usename;
        this.content = requestDto.getContent();
    }

    public void updateComment(com.sparta.hanghaestartproject.v1_api.rest.comment.model.Comment comment){
        this.comment = comment;

    }
    public void update(ReCommentRequestDto requestDto){
        this.content = requestDto.getContent();
    }



    public Long getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public String getContent(){ return content;}

    public Comment getComment(){return comment;}


}
