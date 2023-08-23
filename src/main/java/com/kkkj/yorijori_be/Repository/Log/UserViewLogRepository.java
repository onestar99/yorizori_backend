package com.kkkj.yorijori_be.Repository.Log;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.User.UserViewLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserViewLogRepository extends JpaRepository<UserViewLogEntity, Long> {

    void deleteByRecipe(RecipeEntity recipe);
}
