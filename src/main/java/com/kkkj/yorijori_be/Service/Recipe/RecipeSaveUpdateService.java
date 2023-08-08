package com.kkkj.yorijori_be.Service.Recipe;

import com.kkkj.yorijori_be.Dto.Recipe.RecipeDetailDto;
import com.kkkj.yorijori_be.Dto.Recipe.RecipeDto;
import com.kkkj.yorijori_be.Dto.Recipe.RecipePostDto;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeDetailEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeRepository;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeSaveUpdateService {


    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;


    public void saveRecipe(String userTokenId, RecipeDto recipeDto){

        // TokenId를 통해 유저 정보 찾기
        UserEntity userEntity = userRepository.findByUserTokenId(userTokenId);
        // 전달받은 DTO를 Entity로 변경
        RecipeEntity recipeEntity = recipeDto.toEntity();
        // recipe Entity 유저 정보 세팅
        recipeEntity.setUser(userEntity);
        // User에 recipe Entity 추가
        userEntity.getRecipes().add(recipeEntity);

        System.out.println(userEntity.getRecipes().get(0).getRecipeTitle());
        System.out.println(userEntity.getUserTokenId());


        // 저장
        userRepository.save(userEntity);

    }


    public void saveRecipeDetails(RecipePostDto recipePostDto){
        recipePostDto.getRecipeId();

        // TokenId를 통해 유저 정보 찾기
        RecipeEntity recipeEntity = recipeRepository.findByRecipeId(recipePostDto.getRecipeId());


        for(int i = 0; i < recipePostDto.getRecipeDetailDtoList().size(); i++){
            RecipeDetailDto recipeDetailDto = recipePostDto.getRecipeDetailDtoList().get(i);
            RecipeDetailEntity recipeDetailEntity = recipeDetailDto.toEntity();
            recipeDetailEntity.setOrder(i+1);
            recipeDetailEntity.setRecipe(recipeEntity);
            recipeEntity.getDetails().add(recipeDetailEntity);
            recipeRepository.save(recipeEntity);
        }

//        for(RecipeDetailDto recipeDetailDto : recipePostDto.getRecipeDetailDtoList()){
//            RecipeDetailEntity recipeDetailEntity = recipeDetailDto.toEntity();
//            recipeDetailEntity.setOrder()
//            recipeDetailEntity.setRecipe(recipeEntity);
//            recipeEntity.getDetails().add(recipeDetailEntity);
//            recipeRepository.save(recipeEntity);
//        }
    }

    @Transactional
    public void updateRecipeHits(Long recipeId){
        recipeRepository.updateView(recipeId);
    }



}
