package com.kkkj.yorijori_be.Entity.Recipe;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recipe_detail_entity")
public class RecipeDetailEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_detail_id")
    private Integer recipeDetailId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "recipe")
    private RecipeEntity recipe;

    @Column(name = "recipe_detail", nullable = false)
    private String recipeDetail;

    @Column(name = "recipe_image")
    private String recipeImage;

    @Column(name = "ingredient")
    private String ingredient; // 재료를

    @Column(name = "count")
    private String count; // 몇개

    @Column(name = "time")
    private String time; // 몇분

    @Column(name = "object")
    private String object; // 물체를


    // RecipeId setting
    public void setRecipe(RecipeEntity recipe) {
        this.recipe = recipe;
    }


}
