package com.kkkj.yorijori_be.Controller.Recipe;

import com.kkkj.yorijori_be.Dto.Recipe.RecipeDetailReviewDto;
import com.kkkj.yorijori_be.Dto.Recipe.RecipeDetailsDto;
import com.kkkj.yorijori_be.Dto.Recipe.RecipeListDto;
import com.kkkj.yorijori_be.Service.Recipe.RecipeGetService;
import com.kkkj.yorijori_be.Service.Recipe.RecipeRecommendService;
import com.kkkj.yorijori_be.Service.Recipe.RecipeSaveUpdateService;
import com.kkkj.yorijori_be.Service.User.UserSaveUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Controller
@RequiredArgsConstructor
@RequestMapping("/recipe/get")
public class RecipeGetController {

    private final RecipeGetService recipeGetService;
    private final RecipeSaveUpdateService recipeSaveUpdateService;
    private final UserSaveUpdateService userSaveUpdateService;
    private final RecipeRecommendService recipeRecommendService;


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
    @GetMapping("/details")
    public RecipeDetailsDto getRecipeDetails(
            @RequestParam(value = "recipeId", required = false) Long recipeId,
            @RequestParam(value = "userId", required = false) String userId){

        // 레시피 조회이므로 조회수 1 올리기.
        recipeSaveUpdateService.updateRecipeHits(recipeId);
        // DTO 만들기
        RecipeDetailsDto recipeDetailsDto = recipeGetService.getRecipeDetailsByRecipeId(recipeId);
        // 유저 로그
        if(userId != null & !Objects.equals(userId, "null")){
            userSaveUpdateService.saveUserLog(userId,recipeId);
        }
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

    @ResponseBody
    @GetMapping("/rank")
    public Page<RecipeListDto> getRecipePagingByHits(@RequestParam(value = "page", defaultValue = "0", required = false) int pageNo) {
        return recipeGetService.getRecipePaging(pageNo, 20, "viewCount");
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
    @GetMapping("/search/food")
    public List<RecipeListDto> getTitleSearchedPaging(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "search") String search){
        if(userId != null & !Objects.equals(userId, "null")){
            userSaveUpdateService.saveSearchedRecipeLog(userId,search);
        }
        return recipeGetService.recipeSearchList(search);

    }


    @ResponseBody
    @GetMapping("/search/ingredient")
    public List<RecipeListDto> getIngredientAllSearchedPaging(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value="search") String search){
        if (userId!=null & !Objects.equals(userId, "null")){
            userSaveUpdateService.saveSearchedIngredientLog(userId,search);
        }
        List<String> ingredients = Arrays.asList(search.split(","));
        return recipeGetService.recipeIngredientAllSearchList(ingredients);
    }

    @ResponseBody
    @GetMapping("/reviews/{boardId}")
    public RecipeDetailReviewDto getDetailReview(@PathVariable Long boardId){

        return recipeGetService.getRecipeDetailReview(boardId);
    }

    // 추천 레시피 정보 넘기기
    @ResponseBody
    @GetMapping("/recommend/{userId}")
    public List<RecipeListDto> sendRequestToFlask(@PathVariable String userId) {
        return recipeRecommendService.recipeRecommendByRecipeId(userId);
    }

}
