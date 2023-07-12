package com.kkkj.yorijori_be.Dto.User;

import com.kkkj.yorijori_be.Entity.User.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

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
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;



    /*
    * DTO를 Entity로 변환해주는 함수
    *
    * @return UserEntity
    * */
    public UserEntity toEntity(){
        UserEntity build = UserEntity.builder()
                .userTokenId(UserTokenId)
                .nickname(NickName)
                .imageAddress(ImageAddress)
                .age(Age)
                .gender(Gender)
                .oauthDivision(OAuthDivision)
                .build();

        return build;
    }

    public static UserDto toUserDto(UserEntity userEntity){

        UserDto userDto = new UserDto();
        userDto.setUserTokenId(userEntity.getUserTokenId());
        userDto.setAge(userEntity.getAge());
        userDto.setGender(userEntity.getGender());
        userDto.setImageAddress(userEntity.getImageAddress());
        userDto.setNickName(userEntity.getNickname());
        userDto.setOAuthDivision(userEntity.getOauthDivision());
        userDto.setCreatedTime(userEntity.getCreatedTime());
        userDto.setUpdatedTime(userEntity.getUpdatedTime());

        return userDto;

    }

}
