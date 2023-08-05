package com.kkkj.yorijori_be.Security.Login;

import com.kkkj.yorijori_be.Dto.User.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class LoginDto {

    private String status;
    private String accessToken;
    private UserDto user;

}
