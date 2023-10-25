package com.kkkj.yorijori_be.Entity.Recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recipe_template")
public class RecipeTemplateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_template_id")
    private long templateId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "recipe_detail_id")
    private RecipeDetailEntity recipeDetail;



    @Column(name = "condition_pre")
    private String condition; // 조건

    @Column(name = "ingredient")
    private String ingredient; // 재료를

    @Column(name = "size")
    private String size; // 몇개

    @Column(name = "time")
    private String time; // 몇분

    @Column(name = "tool")
    private String tool; // 물체를

    @Column(name = "action")
    private String action; // 행위




//    // RecipeDetailId setting
//    public void setRecipeDetail(RecipeDetailEntity recipeDetail) {
//        this.recipeDetail = recipeDetail;
//    }
    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTool(String tool) {
        this.tool = tool;
    }

    public void setAction(String action) {
        this.action = action;
    }


}
