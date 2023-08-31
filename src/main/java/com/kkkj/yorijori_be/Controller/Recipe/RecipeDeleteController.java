package com.kkkj.yorijori_be.Controller.Recipe;


import com.kkkj.yorijori_be.Service.Log.LogDeleteService;
import com.kkkj.yorijori_be.Service.Recipe.RecipeDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recipe/delete")
public class RecipeDeleteController {

    private final RecipeDeleteService recipeDeleteService;
    private final LogDeleteService logDeleteService;

    @ResponseBody
    @DeleteMapping("/{recipeId}")
    public String DeleteRecipe(@PathVariable Long recipeId){

        // 레시피 view 로그 삭제
        boolean a = logDeleteService.DeleteUserViewLogsByRecipeId(recipeId);
        // 레시피 재료태그 삭제
        boolean b = recipeDeleteService.DeleteIngredientTagsByRecipeId(recipeId);
        // 레시피 디테일 삭제
        boolean c = recipeDeleteService.DeleteRecipeDetailsByRecipeId(recipeId);
        // 레시피 카테고리 삭제
        boolean d = recipeDeleteService.DeleteRecipeCategoriesByRecipeId(recipeId);
        // 레시피 삭제
        boolean e = recipeDeleteService.DeleteRecipeByRecipeId(recipeId);


        return "hello";

    }


}
