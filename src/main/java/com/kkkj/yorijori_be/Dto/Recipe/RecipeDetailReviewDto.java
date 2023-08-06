package com.kkkj.yorijori_be.Dto.Recipe;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
@Builder
public class RecipeDetailReviewDto {

    private String status;
    private List<Long> starCount;
    private List<ReviewDto> reviews;


}
