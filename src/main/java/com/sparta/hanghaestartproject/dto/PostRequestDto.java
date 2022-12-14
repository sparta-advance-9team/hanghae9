package com.sparta.hanghaestartproject.dto;

import com.sparta.hanghaestartproject.entity.Category;
import com.sparta.hanghaestartproject.entity.CategoryEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostRequestDto {
     private String title;
     private String content;
     private List<CategoryEnum> categories;
}
