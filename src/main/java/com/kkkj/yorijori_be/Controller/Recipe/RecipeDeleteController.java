package com.kkkj.yorijori_be.Controller.Recipe;


import com.kkkj.yorijori_be.Service.Log.LogDeleteService;
import com.kkkj.yorijori_be.Service.Recipe.RecipeDeleteService;
import com.kkkj.yorijori_be.Service.User.UserDeleteService;
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
    private final UserDeleteService userDeleteService;

    @ResponseBody
    @DeleteMapping("/{recipeId}")
    public String DeleteRecipe(@PathVariable Long recipeId){

        // 레시피 view 로그 삭제
        boolean a = logDeleteService.deleteUserViewLogsByRecipeId(recipeId);
        // 레시피 코맨트 삭제
        boolean b = userDeleteService.deleteAllCommentByRecipeId(recipeId);
        // 레시피 재료태그 삭제
        boolean c = recipeDeleteService.deleteIngredientTagsByRecipeId(recipeId);
        // 레시피 템플릿 삭제
        boolean d = recipeDeleteService.deleteRecipeTemplateByRecipeId(recipeId);
        // 레시피 디테일 삭제
        boolean e = recipeDeleteService.deleteRecipeDetailsByRecipeId(recipeId);
        // 레시피 카테고리 삭제
        boolean f = recipeDeleteService.deleteRecipeCategoriesByRecipeId(recipeId);
        // 레시피 삭제
        boolean g = recipeDeleteService.deleteRecipeByRecipeId(recipeId);

        // 모두가 성공하면
        if(a && b && c && d && e && f && g){
            return "delete success";
        }
        return "delete fail";

    }


}
