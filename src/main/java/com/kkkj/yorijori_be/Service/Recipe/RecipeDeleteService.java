package com.kkkj.yorijori_be.Service.Recipe;

import com.kkkj.yorijori_be.Cloud.S3Remover;
import com.kkkj.yorijori_be.Entity.Recipe.*;
import com.kkkj.yorijori_be.Repository.Recipe.*;
import com.kkkj.yorijori_be.Service.Log.LogDeleteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeDeleteService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientTagRepository recipeIngredientTagRepository;
    private final RecipeDetailRepository recipeDetailRepository;
    private final RecipeCategoryTagRepository recipeCategoryTagRepository;
    private final RecipeTemplateRepository recipeTemplateRepository;
    private final S3Remover s3Remover;


    /*
    * ---레시피 재료 튜플 삭제함수---
    * 레시피 아이디를 받는다.
    * 레시피 재료테이블에서 레시피 아이디를 조회하고 삭제한다.
    * 성공하면 True
    * 실패하면 False
    * */
    @Transactional
    public boolean deleteIngredientTagsByRecipeId(long recipeId){
        RecipeEntity recipe = recipeRepository.findByRecipeId(recipeId);
        if(recipe != null){
            List<RecipeIngredientTagEntity> Ingredients = recipe.getIngredients();
            recipeIngredientTagRepository.deleteAllInBatch(Ingredients);
            recipeIngredientTagRepository.flush();
            return true;
        }
        return false;
    }

    /*
    * ---레시피 디테일 튜플 삭제함수---
    * 레시피 아이디를 받는다.
    * 레시피 디테일 테이블에서 레시피 아이디를 조회하여 이미지들을 S3에서 삭제한다.
    * 레시피 디테일 테이블에서 튜플들을 삭제한다.
    * 성공하면 True
    * 실패하면 False
    * */
    @Transactional
    public boolean deleteRecipeDetailsByRecipeId(long recipeId){

        RecipeEntity recipe = recipeRepository.findByRecipeId(recipeId);
        if(recipe != null){
            List<RecipeDetailEntity> details = recipe.getDetails();
            // S3 삭제
            for(RecipeDetailEntity recipeDetail : details) {
                String image = recipeDetail.getRecipeImage();
                if(image != null){
                    String result = s3Remover.deleteFile(image);
                    System.out.println(result);
                }
            }
            // DB 삭제
            recipeDetailRepository.deleteAllInBatch(details);
            recipeDetailRepository.flush();
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteRecipeDetailsExceptsImageByRecipeId(long recipeId){

        RecipeEntity recipe = recipeRepository.findByRecipeId(recipeId);
        if(recipe != null){
            List<RecipeDetailEntity> details = recipe.getDetails();
            // S3 삭제
//            for(RecipeDetailEntity recipeDetail : details) {
//                String image = recipeDetail.getRecipeImage();
//                if(image != null){
//                    String result = s3Remover.deleteFile(image);
//                    System.out.println(result);
//                }
//            }
            // DB 삭제
            recipeDetailRepository.deleteAllInBatch(details);
            recipeDetailRepository.flush();
            return true;
        }
        return false;
    }

    /*
    * ---레시피 카테고리 튜플 삭제함수---
    * 레시피 아이디를 받는다.
    * 레시피 카테고리 테이블에서 레시피 아이디를 조회하고 삭제한다.
    * 성공하면 True
    * 실패하면 False
    * */
    @Transactional
    public boolean deleteRecipeCategoriesByRecipeId(long recipeId){

        RecipeEntity recipe = recipeRepository.findByRecipeId(recipeId);
        if(recipe != null){
            List<RecipeCategoryTagEntity> categories = recipe.getCategories();

            recipeCategoryTagRepository.deleteAllInBatch(categories);
            recipeCategoryTagRepository.flush();
            return true;
        }
        return false;
    }



    /*
     * ---레시피 템플릿 튜플 삭제함수---
     * 레시피 아이디를 받는다.
     * 레시피 템플릿 테이블에서 레시피 아이디를 조회하고 삭제한다.
     * 성공하면 True
     * 실패하면 False
     * */
    @Transactional
    public boolean deleteRecipeTemplateByRecipeId(long recipeId){

        RecipeEntity recipe = recipeRepository.findByRecipeId(recipeId);
        if(recipe != null){
            List<RecipeDetailEntity> details = recipe.getDetails();

            for(RecipeDetailEntity recipeDetail: details){
                List<RecipeTemplateEntity> recipeTemplateEntityList = recipeDetail.getTemplates();
                recipeTemplateRepository.deleteAllInBatch(recipeTemplateEntityList);
                recipeTemplateRepository.flush();
            }

            return true;
        }
        return false;
    }

    /*
     * ---레시피 튜플 삭제함수---
     * 레시피 아이디를 받는다.
     * 레시피 썸네일 이미지를 S3에서 삭제한다.
     * 레시피 테이블에서 레시피 아이디를 조회하고 삭제한다.
     * 성공하면 True
     * 실패하면 False
     * */
    @Transactional
    public boolean deleteRecipeByRecipeId(long recipeId){
        RecipeEntity recipe = recipeRepository.findByRecipeId(recipeId);
        if(recipe != null){
            // S3에서 이미지 삭제
            String image = recipe.getRecipeThumbnail();
            if(image != null){
                String result = s3Remover.deleteFile(image);
                System.out.println(result);
            }
            // 레시피 삭제
            recipeRepository.deleteByRecipeId(recipeId);
//            recipeRepository.flush();
            return true;
        }
        return false;
    }


}
