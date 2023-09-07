package com.kkkj.yorijori_be.Dto.Tip;

import com.kkkj.yorijori_be.Entity.User.UserTipCommentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TipReviewSaveDto {
    private String text;
    private String userId;

    public UserTipCommentEntity toEntity(){
        UserTipCommentEntity userTipCommentEntity = UserTipCommentEntity.builder()
                .comment(text)
                .build();
        return userTipCommentEntity;
    }
}
