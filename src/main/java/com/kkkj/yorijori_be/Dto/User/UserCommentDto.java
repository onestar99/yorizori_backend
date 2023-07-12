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

    private String comment;
    private String scope;


    public UserCommentEntity toEntity(){
        UserCommentEntity build = UserCommentEntity.builder()
                .comment(comment)
                .scope(scope)
                .build();

        return build;
    }

    public UserCommentDto toUserCommentDto(UserCommentEntity userCommentEntity){
        UserCommentDto userCommentDto = new UserCommentDto();
        userCommentDto.setComment(userCommentEntity.getComment());
        userCommentDto.setScope(userCommentEntity.getScope());

        return userCommentDto;
    }


}
