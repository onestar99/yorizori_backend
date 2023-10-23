package com.kkkj.yorijori_be.Entity.Recipe;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "recipe_category_tag")
public class RecipeCategoryTagEntity {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipe;



    @Column(name = "category")
    private String category;

    public void setCategory(String category) {
        this.category = category;
    }
}
