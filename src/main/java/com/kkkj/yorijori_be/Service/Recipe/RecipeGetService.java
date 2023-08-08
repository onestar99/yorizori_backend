package com.kkkj.yorijori_be.Service.Recipe;

import com.kkkj.yorijori_be.Dto.Recipe.*;
import com.kkkj.yorijori_be.Dto.User.UserCommentDto;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeDetailEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeIngredientTagEntity;
import com.kkkj.yorijori_be.Entity.User.UserCommentEntity;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeCategoryTagRepository;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeDetailRepository;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeIngredientTagRepository;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeRepository;
import com.kkkj.yorijori_be.Repository.User.UserCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeGetService {

    private final RecipeRepository recipeRepository;
    private final RecipeDetailRepository recipeDetailRepository;
    private final RecipeIngredientTagRepository recipeIngredientTagRepository;
    private final RecipeCategoryTagRepository recipeCategoryTagRepository;
    private final UserCommentRepository userCommentRepository;

    // 레시피 정보 페이징해서 보내기.
    public Page<RecipeListDto> getRecipePaging(int pageNo, int pageSize, String sortBy){

        // json 형식을 Entity에 맞춰서 칼럼 명칭 변환
        String columnName = sortToColumnName(sortBy);

        System.out.println(columnName);
        // 페이지 인스턴스 생성
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(columnName).descending());
        Page<RecipeEntity> recipeEntityPage = recipeRepository.findAll(pageable);
        Page<RecipeListDto> recipeListDtoPage = RecipeListDto.toDtoPage(recipeEntityPage);
        return recipeListDtoPage;
    }

    public Page<RecipeListDto> getRecipeCategoryPaging(int pageNo, int pageSize, String categoryName){

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("recipeId").descending());
        // 카테고리 이름 이용해서 레시피 아이디들을 받기
        List<Long> recipeIdList = getRecipeIdsByCategory(categoryName);
        Page<RecipeEntity> recipeEntityPage = recipeRepository.findAllByRecipeIdIn(recipeIdList, pageable);
        Page<RecipeListDto> recipeListDtoPage = RecipeListDto.toDtoPage(recipeEntityPage);
        return recipeListDtoPage;
    }



    // "일식" 카테고리에 해당하는 recipe_id들을 가져오는 로직
    private List<Long> getRecipeIdsByCategory(String category) {
        // ... 로직을 구현해야 함
        // 예를 들어, 'recipe_category' 테이블에서 "일식" 카테고리에 해당하는 recipe_id들을 조회하는 쿼리를 실행하고 결과를 반환
        List<Long> recipeEntityList = recipeRepository.findRecipeIdByCategory(category);
        return recipeEntityList;

    }


    // 레시피 디테일 정보 보내주기
    public RecipeDetailsDto getRecipeDetailsByRecipeId(Long recipeId){

        // recipeEntity 만들기
        RecipeEntity recipeEntity = recipeRepository.findByRecipeId(recipeId);

        // List<RecipeIngredientDto> mainIngredient 만들기
        List<RecipeIngredientTagEntity> recipeIngredientMainList = recipeIngredientTagRepository.findByRecipeAndIsMain(recipeEntity, "main");
        List<RecipeIngredientDto> recipeIngredientMainDtoList = new ArrayList<>();
        for(RecipeIngredientTagEntity recipeIngredientTagEntity: recipeIngredientMainList){
            recipeIngredientMainDtoList.add(RecipeIngredientDto.toDto(recipeIngredientTagEntity));
        }

        // List<RecipeIngredientDto> semiIngredient 만들기
        List<RecipeIngredientTagEntity> recipeIngredientSemiList = recipeIngredientTagRepository.findByRecipeAndIsMain(recipeEntity, "semi");
        List<RecipeIngredientDto> recipeIngredientSemiDtoList = new ArrayList<>();
        for(RecipeIngredientTagEntity recipeIngredientTagEntity: recipeIngredientSemiList){
            recipeIngredientSemiDtoList.add(RecipeIngredientDto.toDto(recipeIngredientTagEntity));
        }

        // List<RecipeOrderDto> order 만들기
        List<RecipeDetailEntity> recipeDetailEntityList = recipeEntity.getDetails();
        List<RecipeOrderDto> recipeOrderDtoList = new ArrayList<>();
        for(RecipeDetailEntity recipeDetailEntity: recipeDetailEntityList){
            recipeOrderDtoList.add(RecipeOrderDto.toDto(recipeDetailEntity));
        }

        // 4개 합쳐서 DTO 완성 후 return
        RecipeDetailsDto recipeDetailsDto = RecipeDetailsDto.toDto(recipeEntity, recipeIngredientMainDtoList,
                recipeIngredientSemiDtoList, recipeOrderDtoList);

        return recipeDetailsDto;

    }


    // 조회수순으로 랭크 9위까지 정렬
    public List<RecipeListDto> getTop9ItemsByViews() {

        List<RecipeEntity> recipeEntityList = recipeRepository.findTop9ByOrderByRecipeHitsDesc();
        List<RecipeListDto> recipeListDtoList = new ArrayList<>();
        for(RecipeEntity recipeEntity: recipeEntityList){
            recipeListDtoList.add(RecipeListDto.toDto(recipeEntity));
        }
        return recipeListDtoList;
    }

    // 조회수순으로 랭크 100위까지 정렬
    public List<RecipeListDto> getTop100ItemsByViews() {

        List<RecipeEntity> recipeEntityList = recipeRepository.findTop100ByOrderByRecipeHitsDesc();
        List<RecipeListDto> recipeListDtoList = new ArrayList<>();
        for(RecipeEntity recipeEntity: recipeEntityList){
            recipeListDtoList.add(RecipeListDto.toDto(recipeEntity));
        }
        return recipeListDtoList;
    }




    /*
    * getRecipePaging() 함수에서 현재 사용중인 함수
    * Json 에서 보내오는 변수명과 Java Entity에서의 변수명이 달라 호환을 위해 만든 convert 함수.
    * */
    private String sortToColumnName(String sortBy){
        String columnName = null;
        switch (sortBy){
            case "id":
                columnName = "recipeId";
                break;
            case "viewCount":
                columnName = "recipeHits";
                break;
            case "starRate":
                columnName = "scope";
                break;
        }
        return columnName;
    }

    public List<RecipeListDto> recipeSearchList(String searchKeyword){
        List<RecipeEntity> recipeEntityList = recipeRepository.findByRecipeTitleContaining(searchKeyword);
        List<RecipeListDto> recipeListDtoList = new ArrayList<>();
        for(RecipeEntity recipeEntity : recipeEntityList){
            recipeListDtoList.add(RecipeListDto.toDto(recipeEntity));
        }
        return recipeListDtoList;
    }

    public List<RecipeListDto> recipeIngredientSearchList(String searchKeyword){
        List<RecipeIngredientTagEntity> recipeIngredientTagEntityList = recipeIngredientTagRepository.findByIngredientNameContaining(searchKeyword);
        List<RecipeListDto> recipeListDtoList = new ArrayList<>();
        for(int i=0;i<recipeIngredientTagEntityList.size();i++){
            recipeListDtoList.add(RecipeListDto.toDto(recipeIngredientTagEntityList.get(i).getRecipe()));
        }
        return recipeListDtoList;
    }

    public List<RecipeListDto> recipeIngredientAllSearchList(List<String> ingredients){
//        List<RecipeIngredientTagEntity> recipeIngredientTagEntityList = recipeIngredientTagRepository.findByIngredientNameIn(ingredients);
        List<RecipeListDto> recipeListDtoList = new ArrayList<>();
//        for(int i=0;i<recipeIngredientTagEntityList.size();i++){
//            recipeListDtoList.add(RecipeListDto.toDto(recipeIngredientTagEntityList.get(i).getRecipe()));
//        }
        for(int i=0;i<ingredients.size();i++) {
            List<RecipeIngredientTagEntity> recipeIngredientTagEntityList = new ArrayList<>();
            recipeIngredientTagEntityList = recipeIngredientTagRepository.findByIngredientNameContaining(ingredients.get(i));
            if(i==0){
                for (int j = 0; j < recipeIngredientTagEntityList.size(); j++) {
                    recipeListDtoList.add(RecipeListDto.toDto(recipeIngredientTagEntityList.get(j).getRecipe()));
                }
            }
            else{
                List<RecipeListDto> temprecipeListDtoList = new ArrayList<>();
                for (int j = 0; j < recipeIngredientTagEntityList.size(); j++) {
                    temprecipeListDtoList.add(RecipeListDto.toDto(recipeIngredientTagEntityList.get(j).getRecipe()));
                }
                List<RecipeListDto> finalRecipeListDtoList = recipeListDtoList;
                List<RecipeListDto> matchList = temprecipeListDtoList.stream().filter(o -> finalRecipeListDtoList.stream().anyMatch(n->{return o.getId().equals(n.getId());})).collect(Collectors.toList());
                recipeListDtoList=matchList;
            }
        }
        return recipeListDtoList;
    }


    // 레시피 디테일을 보낼 때 리뷰를 보내줌.
    public RecipeDetailReviewDto getRecipeDetailReview(Long boardId) {

        RecipeEntity recipe = recipeRepository.findByRecipeId(boardId);

        List<Integer> starCount = new ArrayList<>();
        List<ReviewDto> reviewDtoList = new ArrayList<>();

        // starCount 만들기
        starCount.add(userCommentRepository.countCommentsWithScope5ForBoard(boardId));
        starCount.add(userCommentRepository.countCommentsWithScope4ForBoard(boardId));
        starCount.add(userCommentRepository.countCommentsWithScope3ForBoard(boardId));
        starCount.add(userCommentRepository.countCommentsWithScope2ForBoard(boardId));
        starCount.add(userCommentRepository.countCommentsWithScope1ForBoard(boardId));


        // ReviewDto 만들기
        List<UserCommentEntity> userCommentEntityList = userCommentRepository.findByBoardOrderByCreatedTimeDesc(recipe);
        for(UserCommentEntity userCommentEntity: userCommentEntityList){
            reviewDtoList.add(ReviewDto.toDto(userCommentEntity));
        }

        String status = "ok";
        if(userCommentEntityList.isEmpty()){
            status = "null";
        }

        return RecipeDetailReviewDto.builder()
                .status(status)
                .starRate(recipe.getScope())
                .starCount(starCount)
                .reviewCount(recipe.getReviewCount())
                .reviews(reviewDtoList)
                .build();

    }
}
