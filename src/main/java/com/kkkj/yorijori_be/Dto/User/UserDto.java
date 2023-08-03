package com.kkkj.yorijori_be.Dto.User;

import com.kkkj.yorijori_be.Entity.User.UserEntity;
import lombok.*;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@Builder
public class UserDto {

    private String userTokenId;
    private String nickname;
    private String imageAddress;
    private String age;
    private String gender;
    private String oauthDivision;
    private String createdTime;
    private String updatedTime;



    /*
    * DTO를 Entity로 변환해주는 함수
    *
    * @return UserEntity
    * */
    public UserEntity toEntity(){
        UserEntity build = UserEntity.builder()
                .userTokenId(userTokenId)
                .nickname(nickname)
                .imageAddress(imageAddress)
                .age(age)
                .gender(gender)
                .oauthDivision(oauthDivision)
                .build();

        return build;
    }

    public static UserDto toUserDto(UserEntity userEntity){

        UserDto userDto = UserDto.builder()
                .userTokenId(userEntity.getUserTokenId())
                .age(userEntity.getAge())
                .gender(userEntity.getGender())
                .imageAddress(userEntity.getImageAddress())
                .nickname(userEntity.getNickname())
                .oauthDivision(userEntity.getOauthDivision())
                .createdTime(userEntity.getCreatedTime().format(DateTimeFormatter.ISO_DATE))
                .updatedTime(userEntity.getUpdatedTime().format(DateTimeFormatter.ISO_DATE))
                .build();

        return userDto;

    }

}
