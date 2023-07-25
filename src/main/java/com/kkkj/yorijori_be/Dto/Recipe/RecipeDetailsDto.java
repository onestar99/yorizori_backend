package com.kkkj.yorijori_be.Dto.Recipe;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
public class RecipeDetailsDto {

    private long id;
    private String img;
    private String title;
    private String level;
    private String time;
    private String profileImg;
    private String nickname;
    private LocalDateTime date;
    private String explain;
    private List<RecipeIngredientDto> mainIngredient;
    private List<RecipeIngredientDto> semiIngredient;
    private List<RecipeOrderDto> order;


    public static RecipeDetailsDto toDto(RecipeEntity recipeEntity, List<RecipeIngredientDto> mainIngredient,
                                         List<RecipeIngredientDto> semiIngredient, List<RecipeOrderDto> order){

        RecipeDetailsDto recipeDetailsDto = RecipeDetailsDto.builder()
                .id(recipeEntity.getRecipeId())
                .img(recipeEntity.getRecipeThumbnail())
                .title(recipeEntity.getRecipeTitle())
                .level(recipeEntity.getLevel())
                .time(recipeEntity.getTime())
                .profileImg(recipeEntity.getUser().getImageAddress())
                .nickname(recipeEntity.getUser().getNickname())
                .date(recipeEntity.getCreatedTime())
                .explain(recipeEntity.getRecipeIntro())
                .mainIngredient(mainIngredient)
                .semiIngredient(semiIngredient)
                .order(order).build();

        return recipeDetailsDto;
    }

}
