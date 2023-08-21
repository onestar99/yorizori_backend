package com.kkkj.yorijori_be.Dto.Recipe;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class RecipeSaveDto {

    String userId;
    RecipeInfo recipeInfo;
    String thumbnail;
    List<RecipeIngredientSaveDto> mainIngredient;
    List<RecipeIngredientSaveDto> semiIngredient;
    List<RecipeDetailSaveDto> recipeDetail;

}
