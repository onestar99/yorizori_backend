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

    // 레시피 디테일 이미지 제외 삭제 함수
    public boolean deleteRecipeDetailsExceptsImageByRecipeId(long recipeId){
        RecipeEntity recipe = recipeRepository.findByRecipeId(recipeId);
        if(recipe != null){
            List<RecipeDetailEntity> details = recipe.getDetails();
            recipeDetailRepository.deleteAllInBatch(details);
            recipeDetailRepository.flush();
            return true;
        }
        return false;
    }

    /*
    * ---레시피 카테고리 튜플 삭제함수---
    * 레시피 아이디를 받아서 해당 레시피의 카테고리 태그를 삭제
    * */
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
            return true;
        }
        return false;
    }



    /**
     * 레시피와 관련된 모든 데이터를 cascade 방식으로 삭제
     * 전체 작업을 하나의 트랜잭션으로 묶어, 중간에 실패하면 롤백
     */
    @Transactional
    public boolean deleteRecipeCascadeByRecipeId(long recipeId) {
        RecipeEntity recipe = recipeRepository.findByRecipeId(recipeId);
        if (recipe == null) {
            log.warn("Recipe not found for recipeId {}", recipeId);
            return false;
        }

        try {
            // 1. 템플릿 삭제: 레시피 디테일에 포함된 템플릿들을 삭제합니다.
            boolean templateDeleted = deleteRecipeTemplateByRecipeId(recipeId);
            if (!templateDeleted) {
                throw new RuntimeException("Recipe template deletion failed.");
            }

            // 2. 디테일 삭제: 레시피 디테일 및 관련 S3 이미지 삭제 처리
            boolean detailsDeleted = deleteRecipeDetailsByRecipeId(recipeId);
            if (!detailsDeleted) {
                throw new RuntimeException("Recipe details deletion failed.");
            }

            // 3. 재료 태그 삭제
            boolean ingredientTagsDeleted = deleteIngredientTagsByRecipeId(recipeId);
            if (!ingredientTagsDeleted) {
                throw new RuntimeException("Recipe ingredient tags deletion failed.");
            }

            // 4. 카테고리 태그 삭제
            boolean categoriesDeleted = deleteRecipeCategoriesByRecipeId(recipeId);
            if (!categoriesDeleted) {
                throw new RuntimeException("Recipe categories deletion failed.");
            }

            // 5. 최종적으로 레시피 자체 삭제 (썸네일 이미지 삭제 포함)
            boolean recipeDeleted = deleteRecipeByRecipeId(recipeId);
            if (!recipeDeleted) {
                throw new RuntimeException("Recipe deletion failed.");
            }

            return true;
        } catch (Exception e) {
            log.error("Cascade deletion failed for recipeId {}: {}", recipeId, e.getMessage());
            // 예외 발생 시 트랜잭션 롤백을 위해 예외를 다시 throw
            throw e;
        }
    }


}
