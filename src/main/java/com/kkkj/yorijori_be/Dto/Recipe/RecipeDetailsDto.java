package com.kkkj.yorijori_be.Dto.Recipe;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeCategoryTagEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
public class RecipeDetailsDto {

    private long id;
    private String thumbnail;
    private String title;
    private String level;
    private String time;
    private String recipeUserTokenId;
    private String profileImg;
    private String nickname;
    private String date;
    private String explain;
    private List<RecipeIngredientDto> mainIngredient;
    private List<RecipeIngredientDto> semiIngredient;
    private List<RecipeOrderDto> order;
    private List<String> category;
    private int viewCount; // 레시피 조회수


    public static RecipeDetailsDto toDto(RecipeEntity recipeEntity, List<RecipeIngredientDto> mainIngredient,
                                         List<RecipeIngredientDto> semiIngredient, List<RecipeOrderDto> order){

        // 레시피 카테고리를 리스트로 만들기.
        List<String> category = new ArrayList<>();
        List<RecipeCategoryTagEntity> recipeCategoryTagEntities = recipeEntity.getCategories();
        try {
            for(RecipeCategoryTagEntity recipeCategoryTag: recipeCategoryTagEntities){
                category.add(recipeCategoryTag.getCategory());
            }
        } catch (Exception e){
            System.out.println(e);
        }


        RecipeDetailsDto recipeDetailsDto = RecipeDetailsDto.builder()
                .id(recipeEntity.getRecipeId())
                .thumbnail(recipeEntity.getRecipeThumbnail())
                .title(recipeEntity.getRecipeTitle())
                .level(recipeEntity.getLevel())
                .time(recipeEntity.getTime())
                .recipeUserTokenId(recipeEntity.getUser().getUserTokenId())
                .profileImg(recipeEntity.getUser().getImageAddress())
                .nickname(recipeEntity.getUser().getNickname())
                .date(recipeEntity.getCreatedTime().format(DateTimeFormatter.ISO_DATE))
                .explain(recipeEntity.getRecipeIntro())
                .mainIngredient(mainIngredient)
                .semiIngredient(semiIngredient)
                .order(order)
                .category(category)
                .viewCount(recipeEntity.getRecipeViewCount())
                .build();

        return recipeDetailsDto;
    }

}
