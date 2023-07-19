package com.kkkj.yorijori_be.Dto.Recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RecipePostDto {

    private String recipeId;
    private List<RecipeDetailDto> recipeDetailDtoList;

}
