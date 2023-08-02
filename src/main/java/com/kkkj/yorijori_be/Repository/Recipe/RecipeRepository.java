package com.kkkj.yorijori_be.Repository.Recipe;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {


    RecipeEntity findByRecipeId(Long recipeId);

    @Modifying
    @Query("update RecipeEntity r set r.recipeHits = r.recipeHits + 1 where r.recipeId = :id")
    int updateView(@Param("id")Long id);

    List<RecipeEntity> findTop11ByOrderByRecipeHitsDesc();
    List<RecipeEntity> findTop100ByOrderByRecipeHitsDesc();

}
