package com.sparta.hanghaestartproject.v1_api.rest.comment.dto;

import com.sparta.hanghaestartproject.v1_api.rest.comment.model.ReComment;

import java.time.LocalDateTime;

public class ReCommentResponseDto {

    private Long id;
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String username;

    private String content;


    public ReCommentResponseDto(ReComment reComment){
        this.id = reComment.getId();
        this.createdAt = reComment.getCreatedAt();
        this.modifiedAt = reComment.getModifiedAt();
        this.username = reComment.getUsername();
        this.content = reComment.getContent();
    }

    public long getId(){return this.id;}
    public LocalDateTime getCreatedAt(){return this.createdAt;}
    public LocalDateTime getModifiedAt(){return this.modifiedAt;}
    public String getUsername(){return username;}
    public String getContent(){return content;}


}