package com.kkkj.yorijori_be.Controller.Recipe;

import com.kkkj.yorijori_be.Dto.Recipe.RecipeDto;
import com.kkkj.yorijori_be.Dto.Recipe.RecipeSaveDto;
import com.kkkj.yorijori_be.Service.Log.LogDeleteService;
import com.kkkj.yorijori_be.Service.Recipe.RecipeDeleteService;
import com.kkkj.yorijori_be.Service.Recipe.RecipeSaveUpdateService;
import com.kkkj.yorijori_be.Service.User.UserDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recipe/update")
public class RecipeUpdateController {
    private final RecipeSaveUpdateService recipeSaveUpdateService;
    private final RecipeDeleteService recipeDeleteService;
    private final UserDeleteService userDeleteService;
    private final LogDeleteService logDeleteService;


    @PostMapping("/details")
    @ResponseBody
    public long updateRecipeDetail(@RequestBody RecipeSaveDto recipeSaveDto){
        //레시피아이디 저장
        long recipeId = recipeSaveDto.getOriginRecipe();
        // 레시피 view 로그 삭제
//        boolean a = logDeleteService.deleteUserViewLogsByRecipeId(recipeId);
        // 레시피 코맨트 삭제
//        boolean b = userDeleteService.deleteAllCommentByRecipeId(recipeId);
        // 레시피 재료태그 삭제
        boolean c = recipeDeleteService.deleteIngredientTagsByRecipeId(recipeId);
        // 레시피 템플릿 삭제
        boolean d = recipeDeleteService.deleteRecipeTemplateByRecipeId(recipeId);
        // 레시피 디테일 삭제
        boolean e = recipeDeleteService.deleteRecipeDetailsExceptsImageByRecipeId(recipeId);
        // 레시피 카테고리 삭제
        boolean f = recipeDeleteService.deleteRecipeCategoriesByRecipeId(recipeId);
        // 모든 내용이 괜찮은지 검토한다. (Validation)
        RecipeDto recipeDto = RecipeDto.recipeSaveDtoToDTO(recipeSaveDto);
        // 레시피 정보를 업데이트한다.(한개)
        recipeSaveUpdateService.updateRecipe(recipeId, recipeDto);
        // 레시피 디테일 정보와 템플릿을 저장
        recipeSaveUpdateService.saveRecipeDetailsAndTemplates(recipeId, recipeSaveDto);
        // 레시피 재료 정보를 저장(요청-POST)한다. (여러개)
        recipeSaveUpdateService.saveRecipeIngredient(recipeId, recipeSaveDto);
        // 카테고리 저장
        recipeSaveUpdateService.saveRecipeCategory(recipeId, recipeSaveDto.getRecipeInfo().getCategory());

        return recipeId;
    }
}