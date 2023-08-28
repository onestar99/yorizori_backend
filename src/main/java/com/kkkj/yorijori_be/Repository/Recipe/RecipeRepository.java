package com.kkkj.yorijori_be.Repository.Recipe;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeIngredientTagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {


    RecipeEntity findByRecipeId(Long recipeId);

    @Modifying
    @Query("update RecipeEntity r set r.recipeHits = r.recipeHits + 1 where r.recipeId = :id")
    void updateView(@Param("id")Long id);

    List<RecipeEntity> findTop20ByOrderByRecipeHitsDesc();
    List<RecipeEntity> findTop100ByOrderByRecipeHitsDesc();

    List<RecipeEntity> findByRecipeTitleContaining(String searchKeyword);

    // 레시피 아이디를 이용하여 카테고리 조회
    @Query("SELECT r.recipeId FROM RecipeEntity r " +
            "JOIN RecipeCategoryTagEntity rc ON r.recipeId = rc.recipe.recipeId " +
            "WHERE rc.category = :category")
    List<Long> findRecipeIdByCategory(@Param("category") String category);

    // recipeId List를 이용하여 recipe 테이블 컬럼 조회
    Page<RecipeEntity> findAllByRecipeIdIn(List<Long> recipeIdList, Pageable pageable);
    // recipeId를 이용하여 recipe 테이블 컬럼 조회
    Page<RecipeEntity> findAllByUser_UserTokenId(String user_userTokenId, Pageable pageable);

    @Modifying
    @Query("update RecipeEntity r set r.scope = :scope where r.recipeId = :id")
    void updateScope(@Param("scope")String scope, @Param("id")Long id);

    @Modifying
    @Query("update RecipeEntity r set r.reviewCount = :reviewCount where r.recipeId = :id")
    void updateReviewCount(@Param("reviewCount")Integer reviewCount, @Param("id")Long id);

    //재료테이블에서 재료검색값(searchKeyWord)를 재료의 레시피 추출
    @Query(value = "SELECT r.*\n" +
            "FROM recipe AS r\n" +
            "JOIN recipe_ingredient_tag AS rit ON r.recipe_id = rit.recipe_id\n" +
            "WHERE rit.ingredient_name LIKE :searchKeyWord",nativeQuery = true)
    List<RecipeEntity> searchingredient(@Param("searchKeyWord") String searchKeyWord);


    @Modifying
    @Query("DELETE FROM RecipeEntity rl WHERE rl.recipeId = :recipeId")
    void deleteByRecipeId(@Param("recipeId") Long recipeId);

}
