package com.kkkj.yorijori_be.Dto.Recipe;

import com.kkkj.yorijori_be.Entity.User.UserCommentEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;

@Setter
@Getter
@Builder
@ToString
public class ReviewDto {


    private String nickname; // 유저 닉네임
    private String createdTime;// 리뷰 시간
    private String scope;// 별점
    private String review;// 댓글


    public static ReviewDto toDto(UserCommentEntity userCommentEntity){

        return ReviewDto.builder()
                .nickname(userCommentEntity.getUser().getNickname())
                .createdTime(userCommentEntity.getCreatedTime().format(DateTimeFormatter.ISO_DATE))
                .scope(userCommentEntity.getScope())
                .review(userCommentEntity.getComment())
                .build();
    }



}
