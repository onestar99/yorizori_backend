package com.kkkj.yorijori_be.Dto.Recipe;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeDetailEntity;
import lombok.*;

@Setter
@Getter
@ToString
@Builder
public class RecipeOrderDto {

    private String detail;
    private String image;


    // RecipeDetailsDto에 포함시키기 위한 Order List이다.

    public static RecipeOrderDto toDto(RecipeDetailEntity recipeDetailEntity){

        RecipeOrderDto recipeOrderDto = RecipeOrderDto.builder()
                .detail(recipeDetailEntity.getRecipeDetail())
                .image(recipeDetailEntity.getRecipeImage())
                .build();
        return recipeOrderDto;
    }

}
