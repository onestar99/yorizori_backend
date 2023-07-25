package com.kkkj.yorijori_be.Entity.Recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipe_ingredient_tag")
public class RecipeIngredientTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_tag_id")
    private int ingredientTagId;

    @Column(name = "ingredient_name")
    private String ingredientName;

    @Column(name = "ingredient_size")
    private String ingredientSize;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipe;

    @Column(name = "is_main")
    private String isMain;

    // RecipeId setting
    public void setRecipe(RecipeEntity recipe) {
        this.recipe = recipe;
    }


}
