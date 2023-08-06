package com.kkkj.yorijori_be.Entity.Recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kkkj.yorijori_be.Entity.BaseTimeEntity;
import com.kkkj.yorijori_be.Entity.User.UserCommentEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
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

    @Column(name = "recipe_hits", nullable = false)
    private int recipeHits;

    @Column(name = "authorship")
    private String authorship;

    @Column(name = "reference_recipe")
    private String referenceRecipe;

    @Column(name = "scope", length = 4, nullable = false)
    private String scope;

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


}
