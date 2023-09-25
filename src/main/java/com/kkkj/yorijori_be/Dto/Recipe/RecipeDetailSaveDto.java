package com.kkkj.yorijori_be.Dto.Recipe;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class RecipeDetailSaveDto {

    String detail;
    String image;
    List<RecipeTemplateDto> template;
}
