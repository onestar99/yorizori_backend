package com.kkkj.yorijori_be.Repository.Recipe;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeDetailEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeDetailRepository extends JpaRepository<RecipeDetailEntity, Long> {

    List<RecipeDetailEntity> findAllByRecipe_RecipeId(Long recipe_recipeId);

}
