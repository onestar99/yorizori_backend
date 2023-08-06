package com.kkkj.yorijori_be.Service.User;

import com.kkkj.yorijori_be.Dto.User.UserCommentDto;
import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.User.UserCommentEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeRepository;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSaveUpdateService {


    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    // 유저 저장
    @Transactional
    public void saveUser(UserDto userDto){
        userRepository.save(userDto.toEntity());
    }


    @Transactional
    public void updateNickNameById(String tokenId, String NickName){
        UserDto userDto = UserDto.toUserDto(userRepository.findById(tokenId).get());
        userDto.setNickname(NickName);
        userRepository.save(userDto.toEntity());
    }


    @Transactional
    public boolean saveUserComment(Long recipeId, UserCommentDto userCommentDto){

        // BoardId가 있으면 진행
        if (isUserId(userCommentDto)){
            // TokenId를 통해 유저 정보 찾기
            UserEntity userEntity = userRepository.findByUserTokenId(userCommentDto.getUserTokenId());
            // TokenId를 통해 유저 정보 찾기
            RecipeEntity recipeEntity = recipeRepository.findByRecipeId(recipeId);
            // 전달받은 DTO를 Entity로 변경
            UserCommentEntity userCommentEntity = userCommentDto.toEntity();
            // Comment Entity 유저, 레시피 정보 세팅
            userCommentEntity.setUser(userEntity);
            userCommentEntity.setBoard(recipeEntity);
            // User에 Comment Entity 추가
            userEntity.getComments().add(userCommentEntity);
            // 저장
            userRepository.save(userEntity);

            return true;
        } else{
            return false;
        }

    }


    // 레시피 리뷰, 카운트 업데이트
    @Transactional
    public void updateRecipeReviewCountAndScope(Long recipeId){

        double scope = 0.0;
        double mean = 0.0;

        // TokenId를 통해 유저 정보 찾기
        RecipeEntity recipeEntity = recipeRepository.findByRecipeId(recipeId);
        List<UserCommentEntity> userCommentEntityList = recipeEntity.getComments();
        for(UserCommentEntity userCommentEntity: userCommentEntityList){
            scope = scope + Double.parseDouble(userCommentEntity.getScope()); // scope 정보 다 더하기
        }
        mean = scope / userCommentEntityList.size(); // 평점 평균 내기
        mean = (Math.round(mean * 10) / 10.0); // 반올림 처리

        String meanToString = Double.toString(mean); // String으로 전환


        // 업데이트하기
        recipeRepository.updateScope(meanToString, recipeId);
        recipeRepository.updateReviewCount(userCommentEntityList.size(), recipeId);


    }


    // 프로필 이미지주소 업데이트
    @Transactional
    public void updateProfile(String userTokenId, String profileAddress){
        UserEntity userEntity = userRepository.findByUserTokenId(userTokenId);
        userEntity.updateProfile(profileAddress);
    }


    private boolean isUserId(UserCommentDto userCommentDto){
        return userCommentDto.getUserTokenId() != null;
    }



}
