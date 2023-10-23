package com.kkkj.yorijori_be.Repository.Recipe;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeCategoryTagEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeCategoryTagRepository extends JpaRepository<RecipeCategoryTagEntity, Integer> {
    List<RecipeCategoryTagEntity> findAllByRecipe_RecipeId(Long recipe_recipeId);
}
