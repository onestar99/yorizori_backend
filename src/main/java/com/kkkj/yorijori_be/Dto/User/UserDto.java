package com.kkkj.yorijori_be.Dto.User;

import com.kkkj.yorijori_be.Entity.User.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {

    private String UserTokenId;
    private String NickName;
    private String ImageAddress;
    private String Age;
    private String Gender;
    private String OAuthDivision;



    /*
    * DTO를 Entity로 변환해주는 함수
    *
    * @return UserEntity
    * */
    public UserEntity toEntity(){
        UserEntity build = UserEntity.builder()
                .UserTokenId(UserTokenId)
                .NickName(NickName)
                .ImageAddress(ImageAddress)
                .Age(Age)
                .Gender(Gender)
                .OAuthDivision(OAuthDivision)
                .build();

        return build;
    }

}
