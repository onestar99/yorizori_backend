package com.kkkj.yorijori_be.Controller.Recipe;

import com.kkkj.yorijori_be.Dto.Recipe.RecipePostDto;
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


    @PostMapping("/details")
    public ResponseEntity saveRecipe(@RequestBody RecipePostDto recipePostDto){

        System.out.println(recipePostDto.getRecipeId());
        System.out.println(recipePostDto.getRecipeDetailDtoList());

        recipeSaveUpdateService.saveRecipeDetails(recipePostDto);

        // 모든 내용이 괜찮은지 검토한다. (Validation)

        // 레시피 정보를 저장(요청-POST)한다. (한개)
        // 레시피 재료 정보를 저장(요청-POST)한다. (여러개)
        // 레시피 디테일 정보에 있는 이미지들을 s3에 저장(요청-POST-multipart/form-data)한다. (여러개)
        // 이미지들의 주소를 Set하여 레시피 디테일 정보를 저장(요청-POST)한다. (여러개)

        return ResponseEntity.ok("recipe save successfully");
    }


}
