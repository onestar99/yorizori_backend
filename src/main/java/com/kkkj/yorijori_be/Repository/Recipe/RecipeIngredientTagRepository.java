package com.kkkj.yorijori_be.Repository.Recipe;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeIngredientTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeIngredientTagRepository extends JpaRepository<RecipeIngredientTagEntity, Integer> {

    List<RecipeIngredientTagEntity> findByRecipeAndIsMain(RecipeEntity recipe, String isMain);
}
