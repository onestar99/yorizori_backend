package com.kkkj.yorijori_be.Dto.User;

import com.kkkj.yorijori_be.Entity.User.UserViewLogEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserLogDto {
    private LocalDateTime createdTime;

    public UserViewLogEntity toEntity(){
        UserViewLogEntity build = UserViewLogEntity.builder()
                .build();

        return build;
    }
}
