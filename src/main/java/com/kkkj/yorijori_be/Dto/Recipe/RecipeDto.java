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

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RecipeDto {

    private String recipeTitle;
    private List<String> category;
    private String dishName;
    private int recipeViewCount = 0;
    private String authorship;
    private String referenceRecipe;
    private String starCount = "0.0";
    private String recipeIntro;
    private String recipeThumbnail;
    private int reviewCount = 0;
    private String userTokenId;
    private String level;
    private String time;


    static public RecipeDto recipeSaveDtoToDTO(RecipeSaveDto recipeSaveDto){

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setRecipeTitle(recipeSaveDto.getRecipeInfo().getTitle());
        recipeDto.setCategory(recipeSaveDto.getRecipeInfo().getCategory());
        recipeDto.setDishName(recipeSaveDto.getRecipeInfo().getDishName());
        recipeDto.setRecipeViewCount(0);
        recipeDto.setAuthorship(null);
        recipeDto.setStarCount("0.0");
        recipeDto.setRecipeIntro(recipeSaveDto.getRecipeInfo().getExplain());
        String thumbnail = recipeSaveDto.getThumbnail().split("https://yorizori-s3.s3.ap-northeast-2.amazonaws.com")[1];
        recipeDto.setRecipeThumbnail(thumbnail);
        recipeDto.setReviewCount(0);
        recipeDto.setUserTokenId(recipeSaveDto.getUserId());
        recipeDto.setLevel(recipeSaveDto.getRecipeInfo().getLevel());
        recipeDto.setTime(recipeSaveDto.getRecipeInfo().getTime());

        return recipeDto;

    }


    public RecipeEntity toEntity(){
        RecipeEntity build = RecipeEntity.builder()
                .recipeTitle(recipeTitle)
                .dishName(dishName)
                .recipeViewCount(recipeViewCount)
                .recipeIntro(recipeIntro)
                .authorship(authorship)
                .referenceRecipe(referenceRecipe)
                .starCount(starCount)
                .recipeThumbnail(recipeThumbnail)
                .reviewCount(reviewCount)
                .time(time)
                .level(level)
                .build();

        return build;
    }



}
