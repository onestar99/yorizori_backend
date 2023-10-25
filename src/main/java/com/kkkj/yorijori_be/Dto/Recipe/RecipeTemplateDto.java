package com.kkkj.yorijori_be.Dto.Recipe;


import com.kkkj.yorijori_be.Entity.Recipe.RecipeDetailEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeIngredientTagEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeTemplateEntity;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RecipeTemplateDto {

    private String condition; // 조건
    private String ingredient; // 재료
    private String size; // 몇개
    private String time; // 몇분
    private String tool; // 물체
    private String action; // 행위

    public static RecipeTemplateDto toDto(RecipeTemplateEntity recipeTemplateEntity){
        RecipeTemplateDto recipeTemplateDto = RecipeTemplateDto.builder()
                .condition(recipeTemplateEntity.getCondition())
                .ingredient(recipeTemplateEntity.getIngredient())
                .size(recipeTemplateEntity.getSize())
                .time(recipeTemplateEntity.getTime())
                .tool(recipeTemplateEntity.getTool())
                .action(recipeTemplateEntity.getAction())
                .build();

        return recipeTemplateDto;
    }

    public RecipeTemplateEntity toEntity(RecipeDetailEntity recipeDetail){
        RecipeTemplateEntity build = RecipeTemplateEntity.builder()
                .recipeDetail(recipeDetail)
                .condition(condition)
                .ingredient(ingredient)
                .size(size)
                .time(time)
                .tool(tool)
                .action(action)
                .build();

        return build;
    }



}
