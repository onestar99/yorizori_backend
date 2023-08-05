package com.kkkj.yorijori_be.Controller.Recipe;

import com.kkkj.yorijori_be.Dto.Recipe.RecipeDetailsDto;
import com.kkkj.yorijori_be.Dto.Recipe.RecipeListDto;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Service.Recipe.RecipeGetService;
import com.kkkj.yorijori_be.Service.Recipe.RecipeSaveUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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

    // 메인화면에서 rank1부터 rank9까지 불러오는 api
    @ResponseBody
    @GetMapping("/rank/part")
    public List<RecipeListDto> getRecipeRank9(){
        return recipeGetService.getTop9ItemsByViews();
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
        if(categoryName.equals("전체")){ // 카테고리 이름이 all 이면 모든 레시피 조회
            // 레시피 아이디를 뒤집어서 최근 순서대로.
            String sortBy = "id";
            return recipeGetService.getRecipePaging(pageNo, pageSize, sortBy);
        }else{ // 카테고리 이름이 all 이 아니라면 카테고리에 맞춰서 조회
            Page<RecipeListDto> recipeListDtoPage = recipeGetService.getRecipeCategoryPaging(pageNo, pageSize, categoryName);
            return recipeListDtoPage;
        }
    }

    // 검색
    @ResponseBody
    @GetMapping("/searched/recipe")
    public List<RecipeListDto> getTitleSearchedPaging(@RequestParam(value="keyword") String searchKeyword){
        return recipeGetService.recipeSearchList(searchKeyword);
    }

    @ResponseBody
    @GetMapping("/searched/ingredient")
    public List<RecipeListDto> getIngredientSearchedPaging(@RequestParam(value="keyword") String searchKeyword){
        return recipeGetService.recipeIngredientSearchList(searchKeyword);
    }

    @ResponseBody
    @GetMapping("/searched/ingredientall")
    public List<RecipeListDto> getIngredientAllSearchedPaging(@RequestParam(value="keyword") String searchKeywords){
        List<String> ingredients = Arrays.asList(searchKeywords.split(","));
        return recipeGetService.recipeIngredientAllSearchList(ingredients);
    }
}
