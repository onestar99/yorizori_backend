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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public boolean saveUserComment(UserCommentDto userCommentDto){

        // BoardId가 있으면 진행
        if (isBoardId(userCommentDto)){
            // TokenId를 통해 유저 정보 찾기
            UserEntity userEntity = userRepository.findByUserTokenId(userCommentDto.getUserTokenId());
            // TokenId를 통해 유저 정보 찾기
            RecipeEntity recipeEntity = recipeRepository.findByRecipeId(userCommentDto.getBoardId());
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


    // 프로필 이미지주소 업데이트
    @Transactional
    public void updateProfile(String userTokenId, String profileAddress){
        UserEntity userEntity = userRepository.findByUserTokenId(userTokenId);
        userEntity.updateProfile(profileAddress);
    }


    private boolean isBoardId(UserCommentDto userCommentDto){
        return userCommentDto.getBoardId() != null;
    }



}
