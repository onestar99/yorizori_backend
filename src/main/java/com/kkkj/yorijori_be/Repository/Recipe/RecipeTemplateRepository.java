package com.kkkj.yorijori_be.Repository.Recipe;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeDetailEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeTemplateRepository extends JpaRepository<RecipeTemplateEntity, Long> {
    List<RecipeTemplateEntity> findAllByRecipeDetail_RecipeDetailId(Long recipe_recipeId);
}
