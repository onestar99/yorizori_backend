package com.kkkj.yorijori_be.Controller.Recipe;

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
    public Page<RecipeEntity> getAllPaging(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "user_token_id", required = false) String sortBy
    ){
        return recipeGetService.getRecipePaging(pageNo, pageSize, sortBy);
    }

    @ResponseBody
    @GetMapping("/a/{recipeId}")
    public List<RecipeDetailEntity> getRecipeDetails(@PathVariable Long recipeId){

        List<RecipeDetailEntity> recipeDetailEntityList = recipeGetService.getRecipeDetailsByRecipeId(recipeId);
        return recipeDetailEntityList;
    }


}
