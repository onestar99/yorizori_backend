package com.kkkj.yorijori_be.Controller.Recipe;

import com.kkkj.yorijori_be.Dto.Recipe.RecipeDto;
import com.kkkj.yorijori_be.Service.Recipe.RecipeSaveUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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


}
