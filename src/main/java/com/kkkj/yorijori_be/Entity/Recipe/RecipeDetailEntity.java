package com.kkkj.yorijori_be.Entity.Recipe;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recipe_detail")
public class RecipeDetailEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_detail_id")
    private Long recipeDetailId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipe;

    @Column(name = "recipe_detail", nullable = false)
    private String recipeDetail;

    @Column(name = "recipe_image")
    private String recipeImage;

    @Column(name = "order_index")
    private Integer order; // 순서


    @JsonIgnore
    @OneToMany(mappedBy = "recipeDetail", cascade = CascadeType.ALL)
    private List<RecipeTemplateEntity> templates;


    // RecipeId setting
    public void setRecipe(RecipeEntity recipe) {
        this.recipe = recipe;
    }

    // 레시피 디테일 저장할 때 순서 설정
    public void setOrder(int orderIndex) {
        this.order = orderIndex;
    }

    // 레시피 디테일 이미지 업데이트
    public void updateRecipeImage(String recipeImage){
        this.recipeImage = recipeImage;
    }

    public void updaterecipeDetail(String recipeDetail){this.recipeDetail = recipeDetail;}
}
