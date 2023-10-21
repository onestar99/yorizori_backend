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
    private Boolean isEdit;
    private String userId;
    private RecipeInfo recipeInfo;
    private String thumbnail;
    private Long originRecipe;
    private List<RecipeIngredientSaveDto> mainIngredient;
    private List<RecipeIngredientSaveDto> semiIngredient;
    private List<RecipeDetailSaveDto> recipeDetail;

}
