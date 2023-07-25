package com.kkkj.yorijori_be.Controller.Recipe;

import com.kkkj.yorijori_be.Dto.Recipe.RecipeDetailsDto;
import com.kkkj.yorijori_be.Dto.Recipe.RecipeListDto;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeDetailEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Service.Recipe.RecipeGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recipe/get")
public class RecipeGetController {

    private final RecipeGetService recipeGetService;


    // 모든 레시피 정보 페이징 처리
    @GetMapping("/all/paging") @ResponseBody
    public Page<RecipeListDto> getAllPaging(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
    ){
        return recipeGetService.getRecipePaging(pageNo, pageSize, sortBy);
    }

//    @ResponseBody
//    @GetMapping("/details/{recipeId}")
//    public List<RecipeDetailEntity> getRecipeDetails(@PathVariable Long recipeId){
//
//        List<RecipeDetailEntity> recipeDetailEntityList = recipeGetService.getRecipeDetailsByRecipeId(recipeId);
//        return recipeDetailEntityList;
//    }
    @ResponseBody
    @GetMapping("/details/{recipeId}")
    public RecipeDetailsDto getRecipeDetails(@PathVariable Long recipeId){

        RecipeDetailsDto recipeDetailsDto = recipeGetService.getRecipeDetailsByRecipeId(recipeId);

//        List<RecipeDetailEntity> recipeDetailEntityList = recipeGetService.getRecipeDetailsByRecipeId(recipeId);
//        return recipeDetailEntityList;
        return recipeDetailsDto;
    }


}
