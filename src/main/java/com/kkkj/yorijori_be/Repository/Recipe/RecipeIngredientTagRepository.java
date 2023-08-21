package com.kkkj.yorijori_be.Repository.Recipe;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeIngredientTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeIngredientTagRepository extends JpaRepository<RecipeIngredientTagEntity, Integer> {

    List<RecipeIngredientTagEntity> findByRecipeAndIsMain(RecipeEntity recipe, String isMain);

    List<RecipeIngredientTagEntity> findByIngredientNameContaining(String searchKeyword);

    @Query("select r from RecipeIngredientTagEntity r where r.ingredientName in : searchKeyWord")
    List<RecipeIngredientTagEntity> searchingredient(String searchKeyWord);
//    @Query("select r from RecipeIngredientTagEntity r where r.ingredientName in :names")
//    List<RecipeIngredientTagEntity> findByIngredientNameIn(@Param("names") List<String> names);
}
