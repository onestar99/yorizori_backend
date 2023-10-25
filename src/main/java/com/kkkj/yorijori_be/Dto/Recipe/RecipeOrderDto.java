package com.kkkj.yorijori_be.Dto.Recipe;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeDetailEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeTemplateEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@Builder
public class RecipeOrderDto {

    private String detail;
    private String image;
    private List<RecipeTemplateDto> template;

    // RecipeDetailsDto에 포함시키기 위한 Order List이다.

    public static RecipeOrderDto toDto(RecipeDetailEntity recipeDetailEntity){

        List<RecipeTemplateDto> recipeTemplateDtoList = new ArrayList<>();

        for(RecipeTemplateEntity recipeTemplateEntity : recipeDetailEntity.getTemplates()){
            recipeTemplateDtoList.add(RecipeTemplateDto.toDto(recipeTemplateEntity));
        }

        RecipeOrderDto recipeOrderDto = RecipeOrderDto.builder()
                .detail(recipeDetailEntity.getRecipeDetail())
                .image(recipeDetailEntity.getRecipeImage())
                .template(recipeTemplateDtoList)
                .build();
        return recipeOrderDto;
    }

}
