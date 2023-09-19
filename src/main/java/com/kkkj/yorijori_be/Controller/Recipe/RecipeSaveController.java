package com.kkkj.yorijori_be.Controller.Recipe;

import com.kkkj.yorijori_be.Dto.Recipe.RecipeIngredientDto;
import com.kkkj.yorijori_be.Dto.Recipe.RecipePostDto;
import com.kkkj.yorijori_be.Dto.Recipe.RecipeDto;
import com.kkkj.yorijori_be.Dto.Recipe.RecipeSaveDto;
import com.kkkj.yorijori_be.Service.Recipe.RecipeSaveUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recipe/save")
public class RecipeSaveController {

    private final RecipeSaveUpdateService recipeSaveUpdateService;


    @PostMapping("/{userTokenId}")
    public ResponseEntity saveRecipeInfo(@PathVariable String userTokenId, @RequestBody RecipeDto recipeDto) {
        // 인자로 userTokenId와 Dto를 넘겨서 레시피 정보를 저장해주는 함수
        recipeSaveUpdateService.saveRecipe(userTokenId, recipeDto);
        return ResponseEntity.ok("recipe saved successfully : " + recipeDto.getRecipeTitle());
    }


//    @PostMapping("/details")
//    @ResponseBody
//    public long saveRecipe(@RequestBody RecipeSaveDto recipeSaveDto){
//
//        // 모든 내용이 괜찮은지 검토한다. (Validation) - 현재 미완성
//        RecipeDto recipeDto = RecipeDto.recipeSaveDtoToDTO(recipeSaveDto);
//        // 레시피 정보를 저장(요청-POST)한다. (한개)
//        long recipeId = recipeSaveUpdateService.saveRecipe(recipeSaveDto.getUserId(), recipeDto);
//        // 레시피 디테일 정보 저장
//        recipeSaveUpdateService.saveRecipeDetails(recipeId, recipeSaveDto);
//        // 레시피 재료 정보를 저장(요청-POST)한다. (여러개)
//        recipeSaveUpdateService.saveRecipeIngredient(recipeId, recipeSaveDto);
//        // 카테고리 저장
//        recipeSaveUpdateService.saveRecipeCategory(recipeId, recipeSaveDto.getRecipeInfo().getCategory());
//
//        return recipeId;
//    }

    @PostMapping("/details")
    @ResponseBody
    public long saveRecipe(@RequestBody RecipeSaveDto recipeSaveDto){


        // 모든 내용이 괜찮은지 검토한다. (Validation) - 현재 미완성
        RecipeDto recipeDto = RecipeDto.recipeSaveDtoToDTO(recipeSaveDto);
        // 원작자 저장
        recipeSaveUpdateService.saveReferenceRecipe(recipeDto,recipeSaveDto.getReferenceRecipe());
        // 레시피 정보를 저장(요청-POST)한다. (한개)
        long recipeId = recipeSaveUpdateService.saveRecipe(recipeSaveDto.getUserId(), recipeDto);
        // 레시피 디테일 정보 저장
        recipeSaveUpdateService.saveRecipeDetails(recipeId, recipeSaveDto);
        // 레시피 재료 정보를 저장(요청-POST)한다. (여러개)
        recipeSaveUpdateService.saveRecipeIngredient(recipeId, recipeSaveDto);
        // 카테고리 저장
        recipeSaveUpdateService.saveRecipeCategory(recipeId, recipeSaveDto.getRecipeInfo().getCategory());

        return recipeId;
    }


}
