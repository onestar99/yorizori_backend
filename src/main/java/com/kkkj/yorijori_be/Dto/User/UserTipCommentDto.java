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
    private String starCount;

    public UserTipCommentEntity toEntity(){
        UserTipCommentEntity build = UserTipCommentEntity.builder()
                .comment(text)
                .starCount(starCount)
                .build();

        return build;
    }

}
