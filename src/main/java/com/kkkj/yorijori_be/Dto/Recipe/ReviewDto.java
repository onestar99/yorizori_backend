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
    private String profileImg; // 유저 프로필 이미지
    private String scope;// 별점
    private String review;// 댓글
    private String date;// 리뷰 시간


    public static ReviewDto toDto(UserCommentEntity userCommentEntity){

        return ReviewDto.builder()
                .nickname(userCommentEntity.getUser().getNickname())
                .profileImg(userCommentEntity.getUser().getImageAddress())
                .scope(userCommentEntity.getScope())
                .review(userCommentEntity.getComment())
                .date(userCommentEntity.getCreatedTime().format(DateTimeFormatter.ISO_DATE))
                .build();
    }



}
