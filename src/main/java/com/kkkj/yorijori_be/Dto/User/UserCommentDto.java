package com.kkkj.yorijori_be.Dto.User;

import com.kkkj.yorijori_be.Entity.User.UserCommentEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserCommentDto {

    private String userTokenId;
    private String text;
    private Integer starCount;

    public UserCommentEntity toEntity(){
        UserCommentEntity build = UserCommentEntity.builder()
                .comment(text)
                .starCount(starCount)
                .build();

        return build;
    }

}
