package com.kkkj.yorijori_be.Controller.Recipe;

import com.kkkj.yorijori_be.Dto.Recipe.RecipeDetailsDto;
import com.kkkj.yorijori_be.Dto.Recipe.RecipeListDto;
import com.kkkj.yorijori_be.Service.Recipe.RecipeGetService;
import com.kkkj.yorijori_be.Service.Recipe.RecipeSaveUpdateService;
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
    private final RecipeSaveUpdateService recipeSaveUpdateService;


    // 모든 레시피 정보 페이징 처리
    @GetMapping("/all/paging") @ResponseBody
    public Page<RecipeListDto> getAllPaging(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
    ){
        return recipeGetService.getRecipePaging(pageNo, pageSize, sortBy);
    }


    // 레시피 디테일 정보 recipeId를 파라미터로 받아 RecipeDetailsDto 반환
    @ResponseBody
    @GetMapping("/details/{recipeId}")
    public RecipeDetailsDto getRecipeDetails(@PathVariable Long recipeId){

        // DTO 만들기
        RecipeDetailsDto recipeDetailsDto = recipeGetService.getRecipeDetailsByRecipeId(recipeId);
        // 레시피 조회이므로 조회수 1 올리기.
        recipeSaveUpdateService.updateRecipeHits(recipeId);

        return recipeDetailsDto;
    }

    // 메인화면에서 rank1부터 rank11까지 불러오는 api
    @ResponseBody
    @GetMapping("/rank/part")
    public List<RecipeListDto> getRecipeRank11(){
        return recipeGetService.getTop11ItemsByViews();
    }

    // 랭킹화면에서 rank1부터 rank100까지 불러오는 api
    @ResponseBody
    @GetMapping("/rank/total")
    public List<RecipeListDto> getRecipeTotal100(){
        return recipeGetService.getTop100ItemsByViews();
    }


    // 카테고리별로 12개씩 페이징해서 보내주는 api
    @ResponseBody
    @GetMapping("/category/{categoryName}")
    public Page<RecipeListDto> getRecipeCategoryPaging(@PathVariable String categoryName,
               @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo){
        // 페이지 사이즈 고정
        int pageSize = 12;
        Page<RecipeListDto> recipeListDtoPage = recipeGetService.getRecipeCategoryPaging(pageNo, pageSize, categoryName);
        return recipeListDtoPage;
    }
}
