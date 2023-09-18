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


    public RecipeDetailEntity toEntity(){
        RecipeDetailEntity build = RecipeDetailEntity.builder()
                .recipeDetail(recipeDetail)
                .recipeImage(recipeImage)
                .build();

        return build;
    }
}
