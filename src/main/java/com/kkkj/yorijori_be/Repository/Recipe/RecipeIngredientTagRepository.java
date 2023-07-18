package com.kkkj.yorijori_be.Repository.Recipe;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeIngredientTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientTagRepository extends JpaRepository<RecipeIngredientTagEntity, Integer> {
}
