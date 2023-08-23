package com.kkkj.yorijori_be.Service.Log;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Repository.Log.UserViewLogRepository;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class LogDeleteService {

    private final UserViewLogRepository userViewLogRepository;
    private final RecipeRepository recipeRepository;


    /*
     * ---유저 View 로그 튜플 삭제함수---
     * 레시피 아이디를 받는다.
     * 유저 view 테이블에서 레시피 아이디를 조회하고 삭제한다.
     * */
    public boolean DeleteUserViewLogsByRecipeId(long recipeId){
        RecipeEntity recipe = recipeRepository.findByRecipeId(recipeId);
        if(recipe != null){
            userViewLogRepository.deleteByRecipe(recipe);
            return true;
        }
        return false;
    }


}
