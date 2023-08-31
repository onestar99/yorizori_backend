package com.kkkj.yorijori_be.Dto.Recipe;


import com.kkkj.yorijori_be.Entity.Recipe.RecipeIngredientTagEntity;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
public class RecipeIngredientDto {

    private String name;
    private String detail;



    public RecipeIngredientTagEntity toEntity(){
        RecipeIngredientTagEntity recipeIngredientTagEntity = RecipeIngredientTagEntity.builder()
                .ingredientName(name)
                .ingredientSize(detail).build();
        return recipeIngredientTagEntity;
    }


    public static RecipeIngredientDto toDto(RecipeIngredientTagEntity recipeIngredientTagEntity){
        RecipeIngredientDto recipeIngredientDto = RecipeIngredientDto.builder()
                .name(recipeIngredientTagEntity.getIngredientName())
                .detail(recipeIngredientTagEntity.getIngredientSize())
                .build();

        return recipeIngredientDto;
    }

}
