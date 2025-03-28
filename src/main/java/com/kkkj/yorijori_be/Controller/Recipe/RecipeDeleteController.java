package com.kkkj.yorijori_be.Controller.Recipe;

import com.kkkj.yorijori_be.Service.Recipe.RecipeCascadeDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe/delete")
public class RecipeDeleteController {

    private final RecipeCascadeDeleteService recipeCascadeDeleteService;

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<String> DeleteRecipe(@PathVariable Long recipeId){

        boolean isDeleted = recipeCascadeDeleteService.deleteRecipeCompletely(recipeId);
        if (isDeleted) {
            return ResponseEntity.ok("delete success");
        }
        return ResponseEntity.status(500).body("delete fail");

        // 반성하자.. 이게 뭐니..
//        // 레시피 view 로그 삭제
//        boolean a = logDeleteService.deleteUserViewLogsByRecipeId(recipeId);
//        // 레시피 유저 댓글 삭제
//        boolean b = userDeleteService.deleteAllCommentByRecipeId(recipeId);
//        // 레시피 재료태그 삭제
//        boolean c = recipeDeleteService.deleteIngredientTagsByRecipeId(recipeId);
//        // 레시피 템플릿 삭제
//        boolean d = recipeDeleteService.deleteRecipeTemplateByRecipeId(recipeId);
//        // 레시피 디테일 삭제
//        boolean e = recipeDeleteService.deleteRecipeDetailsByRecipeId(recipeId);
//        // 레시피 카테고리 삭제
//        boolean f = recipeDeleteService.deleteRecipeCategoriesByRecipeId(recipeId);
//        // 레시피 삭제
//        boolean g = recipeDeleteService.deleteRecipeByRecipeId(recipeId);
//
//        // 모두가 성공하면
//        if(a && b && c && d && e && f && g){
//            return "delete success";
//        }
//        return "delete fail";

    }


}
