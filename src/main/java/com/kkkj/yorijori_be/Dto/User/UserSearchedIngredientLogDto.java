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
public class UserSearchedIngredientLogDto {
    private String userTokenId;
    private String Ingredient;
    private int scope;
    private LocalDateTime createdTime;
}
