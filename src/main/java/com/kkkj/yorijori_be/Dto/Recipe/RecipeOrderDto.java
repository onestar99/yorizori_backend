package com.kkkj.yorijori_be.Dto.Recipe;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeDetailEntity;
import lombok.*;

@Setter
@Getter
@ToString
@Builder
public class RecipeOrderDto {

    private String contents;
    private String img;



    public static RecipeOrderDto toDto(RecipeDetailEntity recipeDetailEntity){

        RecipeOrderDto recipeOrderDto = RecipeOrderDto.builder()
                .contents(recipeDetailEntity.getRecipeDetail())
                .img(recipeDetailEntity.getRecipeImage())
                .build();
        return recipeOrderDto;
    }

}
