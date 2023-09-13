package com.kkkj.yorijori_be.Service.User;

import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Entity.User.UserCommentEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Repository.User.UserCommentRepository;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDeleteService {

    private final UserRepository userRepository;
    private final UserCommentRepository userCommentRepository;

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

    public ResponseEntity deleteCommentByCommentId(String userTokenId, long commentId){

        // 들어온 tokenId 값이 commentId의 userTokenId와 동일한지 비교 후 동일하면 삭제, 다르면 실패 보내기
        UserCommentEntity userComment = userCommentRepository.findById(commentId).get();

        if(Objects.equals(userComment.getUser().getUserTokenId(), userTokenId)){ // 동일하다면
            try { // 삭제 시도
                userCommentRepository.deleteById(commentId);
                return ResponseEntity.ok(commentId);
            }catch (Exception e){
                e.getMessage();
                return null;
            }
        } else {
            return null;
        }

    }




}
