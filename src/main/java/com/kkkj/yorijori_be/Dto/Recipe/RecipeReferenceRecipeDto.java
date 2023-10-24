package com.kkkj.yorijori_be.Dto.Recipe;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class RecipeReferenceRecipeDto {

    private long recipeId;
    private String nickName;
    private String profileImage;

}
