package com.kkkj.yorijori_be.Dto.User;

import com.kkkj.yorijori_be.Entity.User.UserCommentEntity;
import com.kkkj.yorijori_be.Entity.User.UserTipCommentEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserTipCommentDto {

    private String userTokenId;
    private String text;
    private Boolean isHeart;

    public UserTipCommentEntity toEntity(){
        UserTipCommentEntity build = UserTipCommentEntity.builder()
                .comment(text)
                .isHeart(isHeart)
                .build();
        return build;
    }

//    public static UserTipCommentDto toUserTipCommentDto(UserTipCommentEntity userTipCommentEntity){
//        UserTipCommentDto userTipCommentDto = new UserTipCommentDto();
//        userTipCommentDto.setUserTokenId(userTipCommentEntity.getUser().getUserTokenId());
//        userTipCommentDto.setText(userTipCommentEntity.getComment());
//        userTipCommentDto.setIsHeart(userTipCommentEntity.getIsHeart());
//        return userTipCommentDto;
//    }
}
