package com.kkkj.yorijori_be.Service.User;

import com.kkkj.yorijori_be.Dto.Tip.TipReviewSaveDto;
import com.kkkj.yorijori_be.Dto.User.UserCommentDto;
import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Dto.User.UserTipCommentDto;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import com.kkkj.yorijori_be.Entity.User.*;
import com.kkkj.yorijori_be.Repository.Log.UserViewLogRepository;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeRepository;
import com.kkkj.yorijori_be.Repository.Tip.TipRepository;
import com.kkkj.yorijori_be.Repository.User.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSaveUpdateService {


    private final UserRepository userRepository;
    private final UserCommentRepository userCommentRepository;
    private final RecipeRepository recipeRepository;
    private final UserViewLogRepository userViewLogRepository;
    private final UserSearchedRecipeRepository userSearchedRecipeRepository;
    private final UserSearchedIngredientRepository userSearchedIngredientRepository;
    private final TipRepository tipRepository;

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

    // 유저 프로필 적용할 때 쓰는 함수
    @Transactional
    public UserDto updateProfileById(String tokenId, String nickName, String image, String gender, String age){

        UserEntity user = userRepository.findByUserTokenId(tokenId);
        if(!Objects.equals(nickName, "null")){
            user.updateNickName(nickName);
        }
        if(!Objects.equals(image, "null")) {
            user.updateProfile(image);
        }
        if(!Objects.equals(gender, "null")) {
            user.updateGender(gender);
        }
        if(!Objects.equals(age, "null")) {
            user.updateAge(age);
        }
        userRepository.save(user);

        return UserDto.toUserDto(user);

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

    @Transactional
    public boolean saveUserTipComment(Long tipId, TipReviewSaveDto tipReviewSaveDto){
        TipEntity tipEntity = tipRepository.findByTipId(tipId);
        if(tipReviewSaveDto.getUserTokenId()!=null){
            // TokenId를 통해 유저 정보 찾기
            UserEntity userEntity = userRepository.findByUserTokenId(tipReviewSaveDto.getUserTokenId());
            UserTipCommentEntity userTipCommentEntity = tipReviewSaveDto.toEntity();
            userTipCommentEntity.setUser(userEntity);
            userTipCommentEntity.setBoard(tipEntity);
            userEntity.getTipComments().add(userTipCommentEntity);
            userRepository.save(userEntity);
            return true;
        }else{
            return false;
        }
    }


    // 레시피 리뷰, 카운트 업데이트
    @Transactional
    public void updateRecipeReviewCountAndStarCount(Long recipeId){

        double mean = 0.0;

        // TokenId를 통해 유저 정보 찾기
        RecipeEntity recipeEntity = recipeRepository.findByRecipeId(recipeId);
        List<UserCommentEntity> userCommentEntityList = recipeEntity.getComments();

        mean = userCommentRepository.averageRecipeStarCountByBoardId(recipeId);
        mean = (Math.round(mean * 10) / 10.0); // 반올림 처리

        String meanToString = Double.toString(mean); // String으로 전환


        // 업데이트하기
        recipeRepository.updateStarCount(meanToString, recipeId);
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

    @Transactional
    public void saveUserLog(String usertokenId, Long recipeId){
        UserEntity userEntity = userRepository.findByUserTokenId(usertokenId);
        RecipeEntity recipeEntity = recipeRepository.findByRecipeId(recipeId);
        UserViewLogEntity userViewLogEntity = new UserViewLogEntity();
        userViewLogEntity.setRecipe(recipeEntity);
        userViewLogEntity.setUser(userEntity);
        userViewLogRepository.save(userViewLogEntity);
    }

    @Transactional
    public void saveSearchedRecipeLog(String usertokenId, String keyWord){
        UserEntity userEntity = userRepository.findByUserTokenId(usertokenId);
        UserSearchedRecipeEntity userSearchedRecipeEntity = new UserSearchedRecipeEntity();
        userSearchedRecipeEntity.setUser(userEntity);
        userSearchedRecipeEntity.setSearchedlog(keyWord);
        userSearchedRecipeRepository.save(userSearchedRecipeEntity);
    }

    @Transactional
    public void saveSearchedIngredientLog(String usertokenId, String keyWord){
        UserEntity userEntity = userRepository.findByUserTokenId(usertokenId);
        UserSearchedIngredientEntity userSearchedIngredientEntity = new UserSearchedIngredientEntity();
        userSearchedIngredientEntity.setUser(userEntity);
        userSearchedIngredientEntity.setSearchedlog(keyWord);
        userSearchedIngredientRepository.save(userSearchedIngredientEntity);
    }
}
