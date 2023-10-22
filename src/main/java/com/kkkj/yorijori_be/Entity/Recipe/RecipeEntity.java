package com.kkkj.yorijori_be.Entity.Recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kkkj.yorijori_be.Entity.BaseTimeEntity;
import com.kkkj.yorijori_be.Entity.User.UserCommentEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Entity.Log.UserViewLogEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recipe")
public class RecipeEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(name = "recipe_title", nullable = false)
    private String recipeTitle;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_token_id")
    private UserEntity user;

    @Column(name = "dish_name")
    private String dishName;

    @Column(name = "recipe_intro")
    private String recipeIntro;

    @Column(name = "recipe_view_count", nullable = false)
    private int recipeViewCount;

    @Column(name = "authorship")
    private String authorship;

    @Column(name = "reference_recipe")
    private String referenceRecipe;

    @Column(name = "star_count", length = 4, nullable = false)
    private String starCount;

    @Column(name = "recipe_thumbnail")
    private String recipeThumbnail;

    @Column(name = "review_count", nullable = false)
    private int reviewCount;

    @Column(name = "level")
    private String level;

    @Column(name = "time")
    private String time;

    // userTokenId setting
    public void setUser(UserEntity user) {
        this.user = user;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeIngredientTagEntity> ingredients;

    @JsonIgnore
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeDetailEntity> details;

    @JsonIgnore
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeCategoryTagEntity> categories;

    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<UserCommentEntity> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<UserViewLogEntity> viewlog;

    // 레시피 썸네일 주소 업데이트
    public void updateThumbnail(String thumbnailAddress) {
        this.recipeThumbnail = thumbnailAddress;
    }
    // 레시피 제목 업데이트
    public void updateRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }
    // 레시피 음식 이름 업데이트
    public void updateDishName(String dishName) {
        this.dishName = dishName;
    }
    // 레시피 인트로 업데이트
    public void updateRecipeIntro(String recipeIntro) {
        this.recipeIntro = recipeIntro;
    }
    // 레시피 원작자 업데이트
    public void updateReferenceRecipe(String referenceRecipe) {
        this.referenceRecipe = referenceRecipe;
    }
    // 레시피 레벨 업데이트
    public void updateLevel(String level) {
        this.level = level;
    }
    // 레시피 시간 업데이트
    public void updateTime(String time) {
        this.time = time;
    }
}
