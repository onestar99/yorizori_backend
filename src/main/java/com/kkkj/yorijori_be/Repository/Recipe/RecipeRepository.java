package com.kkkj.yorijori_be.Repository.Recipe;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {


    RecipeEntity findByRecipeId(Long recipeId);

    @Modifying
    @Query("update RecipeEntity r set r.recipeHits = r.recipeHits + 1 where r.recipeId = :id")
    int updateView(Long id);
}
