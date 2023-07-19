package com.kkkj.yorijori_be.Dto.Recipe;


import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class RecipeDetailDto {

    private String recipeDetail;
    private String recipeImage;
    private String ingredient; // 재료를
    private String count; // 몇개
    private String time; // 몇분
    private String object; // 물체를


}
