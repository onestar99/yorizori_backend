package com.kkkj.yorijori_be.Dto.Recipe;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
public class RecipeListDto {

    private Long id; // 레시피 id
    private String title; // 타이틀 이름
    private String thumbnail; // 썸네일 주소
    private String starRate; // 별점
    private int starCount; // 별점수
    private String profileImg; // 프로필 이미지 주소
    private String nickName; // 회원 닉네임
    private int viewCount; // 조회수
    private int reviewCount; // 댓글 수


    public static Page<RecipeListDto> toDtoPage(Page<RecipeEntity> recipeEntityPage){
        Page<RecipeListDto> recipeListDtoPage = recipeEntityPage.map(m -> RecipeListDto.builder()
                .id(m.getRecipeId())
                .title(m.getRecipeTitle())
                .thumbnail(m.getRecipeThumbnail())
                .starRate(m.getScope())
                .starCount(m.getScopeCount())
                .profileImg(m.getUser().getImageAddress())
                .nickName(m.getUser().getNickname())
                .viewCount(m.getRecipeHits())
                .reviewCount(m.getReviewCount()).build());


        return recipeListDtoPage;
    }


}
