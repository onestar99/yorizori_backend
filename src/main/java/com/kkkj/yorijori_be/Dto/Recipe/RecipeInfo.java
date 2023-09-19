package com.kkkj.yorijori_be.Dto.Recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class RecipeInfo {

    String title;
    String level;
    String time;
    List<String> category;
    String explain;


}
