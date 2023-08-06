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
    private String starRate;
    private List<Long> starCount;
    private Integer reviewCount;
    private List<ReviewDto> reviews;


}
