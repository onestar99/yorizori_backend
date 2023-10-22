package com.kkkj.yorijori_be.Controller.Recipe;

import com.kkkj.yorijori_be.Dto.Recipe.RecipeDto;
import com.kkkj.yorijori_be.Dto.Recipe.RecipeSaveDto;
import com.kkkj.yorijori_be.Service.Recipe.RecipeSaveUpdateService;
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

    @PostMapping("/details")
    @ResponseBody
    public long updateRecipe(@RequestBody RecipeSaveDto recipeSaveDto){


        // 모든 내용이 괜찮은지 검토한다. (Validation) - 현재 미완성
        RecipeDto recipeDto = RecipeDto.recipeSaveDtoToDTO(recipeSaveDto);
        // 레시피 정보를 업데이트(요청-POST)한다. (한개)
        recipeSaveUpdateService.updateRecipe(recipeSaveDto.getUserId(), recipeSaveDto.getOriginRecipe(),recipeDto);
        // 레시피 디테일 정보와 템플릿을 업데이트
        recipeSaveUpdateService.updateRecipeDetailsAndTemplates(recipeSaveDto.getOriginRecipe(), recipeSaveDto);
        // 레시피 재료 정보를 저장(요청-POST)한다. (여러개)
        recipeSaveUpdateService.updateRecipeIngredient(recipeSaveDto.getOriginRecipe(), recipeSaveDto);
        // 카테고리 저장
        recipeSaveUpdateService.updateRecipeCategory(recipeSaveDto.getOriginRecipe(), recipeSaveDto.getRecipeInfo().getCategory());

        return recipeSaveDto.getOriginRecipe();
    }
}
