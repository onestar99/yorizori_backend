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
    private String recipeThumbnail;
    private int reviewCount = 0;
    private String userTokenId;
    private String level;
    private String time;


    static public RecipeDto recipeSaveDtoToDTO(RecipeSaveDto recipeSaveDto){

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setRecipeTitle(recipeSaveDto.recipeInfo.title);
        recipeDto.setCategory(recipeSaveDto.recipeInfo.category);
        recipeDto.setDishName(null);
        recipeDto.setRecipeHits(0);
        recipeDto.setAuthorship(null);
        recipeDto.setScope("0.0");
        recipeDto.setRecipeIntro(recipeSaveDto.recipeInfo.explain);
        String thumbnail = recipeSaveDto.thumbnail.split("https://yorizori-s3.s3.ap-northeast-2.amazonaws.com")[1];
        recipeDto.setRecipeThumbnail(thumbnail);
        recipeDto.setReviewCount(0);
        recipeDto.setUserTokenId(recipeSaveDto.getUserId());
        recipeDto.setLevel(recipeSaveDto.recipeInfo.getLevel());
        recipeDto.setTime(recipeSaveDto.recipeInfo.getTime());

        return recipeDto;

    }


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
                .time(time)
                .level(level)
                .build();

        return build;
    }



}
