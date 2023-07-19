package com.kkkj.yorijori_be.Dto.Recipe;


import com.kkkj.yorijori_be.Entity.Recipe.RecipeDetailEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class RecipeDetailDto {

    private String recipeDetail;
    private String recipeImage;
    private String ingredient; // 재료를
    private String count; // 몇개
    private String time; // 몇분
    private String object; // 물체를

    public RecipeDetailEntity toEntity(){
        RecipeDetailEntity build = RecipeDetailEntity.builder()
                .recipeDetail(recipeDetail)
                .recipeImage(recipeImage)
                .ingredient(ingredient)
                .count(count)
                .time(time)
                .object(object)
                .build();

        return build;
    }
}
