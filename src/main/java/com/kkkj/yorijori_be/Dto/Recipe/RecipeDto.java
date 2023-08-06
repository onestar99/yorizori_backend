package com.kkkj.yorijori_be.Dto.Recipe;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RecipeDto {

    private String recipeTitle;
    private String category;
    private String dishName;
    private int recipeHits = 0;
    private String authorship;
    private String referenceRecipe;
    private String scope = "0.0";
    private String recipeIntro;
    private int scopeCount = 0;
    private String recipeThumbnail;
    private int reviewCount = 0;


    public RecipeEntity toEntity(){
        RecipeEntity build = RecipeEntity.builder()
                .recipeTitle(recipeTitle)
                .dishName(dishName)
                .recipeHits(recipeHits)
                .recipeIntro(recipeIntro)
                .authorship(authorship)
                .referenceRecipe(referenceRecipe)
                .scope(scope)
                .recipeThumbnail(recipeThumbnail)
                .reviewCount(reviewCount)
                .build();

        return build;
    }


}
