package com.kkkj.yorijori_be.Service.Recipe;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeDetailEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeDetailRepository;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeGetService {

    private final RecipeRepository recipeRepository;
    private final RecipeDetailRepository recipeDetailRepository;

    // 레시피 정보 페이징해서 보내기.
    public Page<RecipeEntity> getRecipePaging(int pageNo, int pageSize, String sortBy){

        // 페이지 인스턴스 생성
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return recipeRepository.findAll(pageable);
    }


    // 레시피 디테일 정보 보내주기
    public List<RecipeDetailEntity> getRecipeDetailsByRecipeId(Long recipeId) {

        return recipeRepository.findByRecipeId(recipeId).getDetails();

    }



}
