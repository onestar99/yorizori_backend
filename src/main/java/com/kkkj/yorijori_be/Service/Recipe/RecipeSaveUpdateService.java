package com.kkkj.yorijori_be.Service.Recipe;

import com.kkkj.yorijori_be.Dto.Cloud.FileUploadResponse;
import com.kkkj.yorijori_be.Dto.Recipe.*;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeCategoryTagEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeDetailEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeIngredientTagEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeCategoryTagRepository;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeDetailRepository;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeIngredientTagRepository;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeRepository;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeSaveUpdateService {


    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final RecipeDetailRepository recipeDetailRepository;
    private final RecipeIngredientTagRepository recipeIngredientTagRepository;
    private final RecipeCategoryTagRepository recipeCategoryTagRepository;

    public Long saveRecipe(String userTokenId, RecipeDto recipeDto){

        // TokenId를 통해 유저 정보 찾기
        UserEntity userEntity = userRepository.findByUserTokenId(userTokenId);
        // 전달받은 DTO를 Entity로 변경
        RecipeEntity recipeEntity = recipeDto.toEntity();
        // recipe Entity 유저 정보 세팅
        recipeEntity.setUser(userEntity);
        // User에 recipe Entity 추가
        userEntity.getRecipes().add(recipeEntity);


        // 저장
        UserEntity user = userRepository.save(userEntity);
        int recipeSize = user.getRecipes().size();
        Long recipeId = user.getRecipes().get(recipeSize-1).getRecipeId();

        return recipeId;


    }


    // 레시피 디테일 정보들 저장
    public void saveRecipeDetails(Long recipeId, RecipeSaveDto recipeSaveDto){

        // TokenId를 통해 유저 정보 찾기
        RecipeEntity recipeEntity = recipeRepository.findByRecipeId(recipeId);

        // 레시피 디테일 정보들 저장
        for(int i = 0; i < recipeSaveDto.getRecipeDetail().size(); i++){

            String image = recipeSaveDto.getRecipeDetail().get(i).getImage();
            String splitImage = "";
            if(image != null){
                splitImage = image.split("https://yorizori-s3.s3.ap-northeast-2.amazonaws.com")[1];
            }

            RecipeDetailEntity recipeDetailEntity = RecipeDetailEntity.builder()
                    .recipeDetail(recipeSaveDto.getRecipeDetail().get(i).getText())
                    .recipeImage(splitImage)
                    .recipe(recipeEntity)
                    .order(i+1).build();

            recipeDetailRepository.save(recipeDetailEntity);
        }

    }


    public void saveRecipeIngredient(long recipeId, RecipeSaveDto recipeSaveDto){

        RecipeEntity recipe = recipeRepository.findByRecipeId(recipeId);


        // 반복문을 통해 main 재료 먼저 처리
        for (RecipeIngredientSaveDto recipeIngredientSaveDto: recipeSaveDto.getMainIngredient()){
            RecipeIngredientTagEntity recipeIngredientTagEntity = RecipeIngredientTagEntity.builder()
                    .ingredientName(recipeIngredientSaveDto.getName())
                    .ingredientSize(recipeIngredientSaveDto.getSize())
                    .isMain("main")
                    .recipe(recipe).build();
            recipeIngredientTagRepository.save(recipeIngredientTagEntity);
        }

        // 반복문을 통해 semi 재료 처리
        for (RecipeIngredientSaveDto recipeIngredientSaveDto: recipeSaveDto.getSemiIngredient()){
            RecipeIngredientTagEntity recipeIngredientTagEntity = RecipeIngredientTagEntity.builder()
                    .ingredientName(recipeIngredientSaveDto.getName())
                    .ingredientSize(recipeIngredientSaveDto.getSize())
                    .isMain("semi")
                    .recipe(recipe).build();
            recipeIngredientTagRepository.save(recipeIngredientTagEntity);
        }




    }

    @Transactional
    public void updateRecipeHits(Long recipeId){
        recipeRepository.updateView(recipeId);
    }

    // 프로필 이미지주소 업데이트
    @Transactional
    public void updateThumbnail(Long recipeId, String thumbnailAddress){
        RecipeEntity recipeEntity = recipeRepository.findByRecipeId(recipeId);
        recipeEntity.updateThumbnail(thumbnailAddress);
    }


    /*
    * 레시피 디테일 이미지주소 업데이트.
    * */
    public void updateRecipeDetailImage(Long recipeId, List<FileUploadResponse> fileUploadResponseList) {
        // 레시피 디테일 튜플 뽑아오기
        List<RecipeDetailEntity> recipeDetailEntityList = recipeDetailRepository.findAllByRecipe_RecipeId(recipeId);
        // 업로드된 파일들의 숫자만큼 DB에 업데이트
        for(int i = 0; i < fileUploadResponseList.size(); i++){
            String dbUploadStr = "/" + fileUploadResponseList.get(i).getFileName();
            recipeDetailEntityList.get(i).updateRecipeImage(dbUploadStr);
        }

    }


    // 레시피 카테고리 저장
    public void saveRecipeCategory(long recipeId, List<String> category) {

        RecipeEntity recipe = recipeRepository.findByRecipeId(recipeId);
        // 반복문을 통해 카테고리 여러개 저장
        for(String cate: category){
            RecipeCategoryTagEntity recipeCategoryTag = RecipeCategoryTagEntity.builder()
                    .category(cate)
                    .recipe(recipe).build();
            recipeCategoryTagRepository.save(recipeCategoryTag);
        }
    }

    //레시피 원작자 확인 후 저장
    public void saveReferenceRecipe(RecipeDto recipeDto,Long referenceRecipe){
        if(referenceRecipe!=null){
            RecipeEntity recipeEntity = recipeRepository.findByRecipeId(referenceRecipe);
            if(recipeEntity.getReferenceRecipe()==null){
                String temp = ""+referenceRecipe;
                recipeDto.setReferenceRecipe(temp);
            }else{
                recipeDto.setReferenceRecipe(recipeEntity.getReferenceRecipe()+","+referenceRecipe);
            }
        }
    }
}
