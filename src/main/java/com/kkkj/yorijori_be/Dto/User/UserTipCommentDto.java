package com.kkkj.yorijori_be.Dto.User;

import com.kkkj.yorijori_be.Entity.User.UserCommentEntity;
import com.kkkj.yorijori_be.Entity.User.UserTipCommentEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserTipCommentDto {

    private String review;
    private String nickname;
    private String profileImg;
    private String date;

    public UserTipCommentEntity toEntity(){
        UserTipCommentEntity build = UserTipCommentEntity.builder()
                .comment(review)
                .build();
        return build;
    }

    public static UserTipCommentDto toUserTipCommentDto(UserTipCommentEntity userTipCommentEntity){
        UserTipCommentDto userTipCommentDto = new UserTipCommentDto();
        userTipCommentDto.setReview(userTipCommentEntity.getComment());
        userTipCommentDto.setNickname(userTipCommentEntity.getUser().getNickname());
        userTipCommentDto.setProfileImg(userTipCommentEntity.getUser().getImageAddress());
        if(userTipCommentEntity.getCreatedTime()!=null){
            userTipCommentDto.setDate(userTipCommentEntity.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        }else{
            userTipCommentDto.setDate(null);
        }
        return userTipCommentDto;
    }
}
