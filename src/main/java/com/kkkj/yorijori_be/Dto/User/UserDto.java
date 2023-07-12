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

    private String userTokenId;
    private String nickname;
    private String imageAddress;
    private String age;
    private String gender;
    private String oauthDivision;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;



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

        UserDto userDto = new UserDto();
        userDto.setUserTokenId(userEntity.getUserTokenId());
        userDto.setAge(userEntity.getAge());
        userDto.setGender(userEntity.getGender());
        userDto.setImageAddress(userEntity.getImageAddress());
        userDto.setNickname(userEntity.getNickname());
        userDto.setOauthDivision(userEntity.getOauthDivision());
        userDto.setCreatedTime(userEntity.getCreatedTime());
        userDto.setUpdatedTime(userEntity.getUpdatedTime());

        return userDto;

    }

}
