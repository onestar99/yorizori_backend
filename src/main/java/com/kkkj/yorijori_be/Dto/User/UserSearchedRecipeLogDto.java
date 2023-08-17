package com.kkkj.yorijori_be.Dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserSearchedRecipeLogDto {
    private String userTokenId;
    private String recipeId;
    private int scope;
    private LocalDateTime createdTime;
}
