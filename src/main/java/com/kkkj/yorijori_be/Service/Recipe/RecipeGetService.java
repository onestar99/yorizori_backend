package com.kkkj.yorijori_be.Service.Recipe;

import com.kkkj.yorijori_be.Dto.Recipe.RecipeDetailsDto;
import com.kkkj.yorijori_be.Dto.Recipe.RecipeIngredientDto;
import com.kkkj.yorijori_be.Dto.Recipe.RecipeListDto;
import com.kkkj.yorijori_be.Dto.Recipe.RecipeOrderDto;
import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeDetailEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeIngredientTagEntity;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeDetailRepository;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeIngredientTagRepository;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeGetService {

    private final RecipeRepository recipeRepository;
    private final RecipeDetailRepository recipeDetailRepository;
    private final RecipeIngredientTagRepository recipeIngredientTagRepository;

    // 레시피 정보 페이징해서 보내기.
    public Page<RecipeListDto> getRecipePaging(int pageNo, int pageSize, String sortBy){

        // json 형식을 Entity에 맞춰서 칼럼 명칭 변환
        String columnName = sortToColumnName(sortBy);

        System.out.println(columnName);
        // 페이지 인스턴스 생성
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(columnName).descending());
        Page<RecipeEntity> recipeEntityPage = recipeRepository.findAll(pageable);
        Page<RecipeListDto> recipeListDtoPage = RecipeListDto.toDtoList(recipeEntityPage);
        return recipeListDtoPage;
    }


//    // 레시피 디테일 정보 보내주기
//    public List<RecipeDetailEntity> getRecipeDetailsByRecipeId(Long recipeId) {
//
//        return recipeRepository.findByRecipeId(recipeId).getDetails();
//
//    }


    public RecipeDetailsDto getRecipeDetailsByRecipeId(Long recipeId){

//        (RecipeEntity recipeEntity, List< RecipeIngredientDto > mainIngredient,
//                List<RecipeIngredientDto> semiIngredient, List< RecipeOrderDto > order)

        // recipeEntity 만들기
        RecipeEntity recipeEntity = recipeRepository.findByRecipeId(recipeId);

        // List<RecipeIngredientDto> mainIngredient 만들기
//        List<RecipeIngredientTagEntity> recipeIngredientList = recipeEntity.getIngredients();
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

        RecipeDetailsDto recipeDetailsDto = RecipeDetailsDto.toDto(recipeEntity, recipeIngredientMainDtoList,
                recipeIngredientSemiDtoList, recipeOrderDtoList);

        return recipeDetailsDto;

    }




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

}
