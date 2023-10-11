package com.kkkj.yorijori_be.Repository.Recipe;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeIngredientTagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {


    RecipeEntity findByRecipeId(Long recipeId);

    @Modifying
    @Query("update RecipeEntity r set r.recipeViewCount = r.recipeViewCount + 1 where r.recipeId = :id")
    void updateView(@Param("id")Long id);

    List<RecipeEntity> findTop9ByOrderByRecipeViewCountDesc();
    List<RecipeEntity> findTop100ByOrderByRecipeViewCountDesc();

    Page<RecipeEntity> findByRecipeTitleContaining(String searchKeyword,Pageable pageable);
    List<RecipeEntity> findByRecipeTitleContaining(String ingredient);


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
    @Query("update RecipeEntity r set r.starCount = :starCount where r.recipeId = :id")
    void updateStarCount(@Param("starCount")String starCount, @Param("id")Long id);

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

//    List<RecipeEntity> findByRecipeIdIn(List<Long> ids);


    // 생성 날짜별로 잘라서 레시피 불러오기
    @Query(value = "SELECT * FROM recipe r WHERE r.created_time BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<RecipeEntity> findRecipesBetweenDates(@Param("startDate") String startDate,
                                               @Param("endDate") String endDate);


    // 추천 Weight 적용한 레시피 불러오기 (List 버전)
    @Query(value = "SELECT *, " +
            "(" +
            // star_count가 0일때는 3.0으로 판단
            "0.13 * IF(CAST(star_count AS DECIMAL) = 0, 3.0, CAST(star_count AS DECIMAL)) + " +
            "0.2 * (recipe_view_count / (SELECT MAX(recipe_view_count) FROM recipe)) + " +
            // review_count가 2개 이상일 때 리뷰 점수 적용
            "IF(review_count > 2, 0.5 * (review_count / (SELECT MAX(review_count) FROM recipe)), 0) + " +
            "0.5 * exp(((1 - ((TIMESTAMPDIFF(DAY,created_time, NOW()) / 30) / (SELECT MAX(TIMESTAMPDIFF(DAY, created_time, NOW()) / 30) FROM recipe))) * 20 - 10) / 20)" +
            ") AS score " +
            "FROM recipe ORDER BY score DESC LIMIT :limit",
            nativeQuery = true)
    List<RecipeEntity> findTopRecipes(@Param("limit") int limit);

    // 추천 Weight 적용한 레시피 불러오기 (Page 버전)
    @Query(value = "SELECT *, " +
            "(" +
            // star_count가 0일때는 3.0으로 판단
            "0.13 * IF(CAST(star_count AS DECIMAL) = 0, 3.0, CAST(star_count AS DECIMAL)) + " +
            "0.2 * (recipe_view_count / (SELECT MAX(recipe_view_count) FROM recipe)) + " +
            // review_count가 2개 이상일 때 리뷰 점수 적용
            "IF(review_count > 2, 0.5 * (review_count / (SELECT MAX(review_count) FROM recipe)), 0) + " +
            "0.5 * exp(((1 - ((TIMESTAMPDIFF(DAY,created_time, NOW()) / 30) / (SELECT MAX(TIMESTAMPDIFF(DAY, created_time, NOW()) / 30) FROM recipe))) * 20 - 10) / 20)" +
            ") AS score " +
            "FROM recipe ORDER BY score DESC",
            nativeQuery = true)
    Page<RecipeEntity> findTopRecipesPage(Pageable pageable);


    @Query(value = "SELECT r.*, " +
            "0.13 * IF(CAST(r.star_count AS DECIMAL) = 0, 3.0, CAST(r.star_count AS DECIMAL)) + " +
            "0.2 * (r.recipe_view_count / (SELECT MAX(recipe_view_count) FROM recipe)) + " +
            "IF(r.review_count > 2, 0.5 * (r.review_count / (SELECT MAX(review_count) FROM recipe)), 0) + " +
            "0.5 * exp(((1 - ((TIMESTAMPDIFF(DAY, r.created_time, NOW()) / 30) / " +
            "(SELECT MAX(TIMESTAMPDIFF(DAY, created_time, NOW()) / 30) FROM recipe))) * 20 - 10) / 20) AS score " +
            "FROM recipe r " +
            "INNER JOIN recipe_category_tag t ON r.recipe_id = t.recipe_id " +
            "WHERE t.category = :category " +
            "ORDER BY score DESC",
            nativeQuery = true)
    Page<RecipeEntity> findKoreanRecipesWithWeight(Pageable pageable, @Param("category") String category);



    // 키워드를 이용한 요리조리 랭킹 시스템
    @Query(value = "SELECT r.*, (" +
            "0.13 * IF(CAST(r.star_count AS DECIMAL) = 0, 3.0, CAST(r.star_count AS DECIMAL)) + " +
            "0.2 * (r.recipe_view_count / (SELECT MAX(recipe_view_count) FROM recipe)) + " +
            "IF(r.review_count > 2, 0.5 * (r.review_count / (SELECT MAX(review_count) FROM recipe)), 0) + " +
            "0.5 * exp(((1 - ((TIMESTAMPDIFF(DAY, r.created_time, NOW()) / 30) / " +
            "(SELECT MAX(TIMESTAMPDIFF(DAY, created_time, NOW()) / 30) FROM recipe))) * 20 - 10) / 20)" +
            ") AS score " +
            "FROM recipe r " +
            "WHERE r.recipe_title LIKE %:searchKeyWord% " +
            "ORDER BY score DESC",
            nativeQuery = true)
    Page<RecipeEntity> findRecipesWithWeightAndKeyword(Pageable pageable, @Param("searchKeyWord") String searchKeyWord);


}
