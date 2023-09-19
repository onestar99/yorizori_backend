package com.kkkj.yorijori_be.Service.User;

import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.User.UserCommentEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeRepository;
import com.kkkj.yorijori_be.Repository.User.UserCommentRepository;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDeleteService {

    private final UserRepository userRepository;
    private final UserCommentRepository userCommentRepository;
    private final RecipeRepository recipeRepository;

    // 개인 유저 삭제
    @Transactional
    public void deleteUserById(String tokenId){
        UserDto userDto = UserDto.toUserDto(userRepository.findById(tokenId).get());
        userRepository.delete(userDto.toEntity());
    }



    // UserTokenId를 입력받아 User Comment 전체 삭제.
    // SUCCESSFULLY -> True
    // FAILED -> False
    @Transactional
    public boolean deleteAllCommentById(String tokenId){
        UserEntity user = userRepository.findByUserTokenId(tokenId);
        if (user != null){
            List<UserCommentEntity> comments = user.getComments();
            userCommentRepository.deleteAllInBatch(comments);
            userCommentRepository.flush();
            return true;
        } else {
            return false;
        }
    }

//
//    // 댓글을 남긴 사람과 유저 토큰을 비교하여 일치하면 코맨트 *삭제* 가능하도록 하는 함수
//    public ResponseEntity deleteCommentByCommentId(String userTokenId, long commentId){
//
//        // 들어온 tokenId 값이 commentId의 userTokenId와 동일한지 비교 후 동일하면 삭제, 다르면 실패 보내기
//        UserCommentEntity userComment = userCommentRepository.findById(commentId).get();
//
//        if(Objects.equals(userComment.getUser().getUserTokenId(), userTokenId)){ // 동일하다면
//            try { // 삭제 시도
//                userCommentRepository.deleteById(commentId);
//                return ResponseEntity.ok(commentId);
//            }catch (Exception e){
//                e.getMessage();
//                return null;
//            }
//        } else {
//            return null;
//        }
//
//    }


    // recipeId를 입력받아 User Comment 전체 삭제.
    // SUCCESSFULLY -> True
    // FAILED -> False
    @Transactional
    public boolean deleteAllCommentByRecipeId(long recipeId){
        RecipeEntity recipe = recipeRepository.findByRecipeId(recipeId);
        if (recipe != null){
            List<UserCommentEntity> comments = recipe.getComments();
            userCommentRepository.deleteAllInBatch(comments);
            userCommentRepository.flush();
            return true;
        } else {
            return false;
        }
    }



}
