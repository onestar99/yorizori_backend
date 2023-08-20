package com.kkkj.yorijori_be.Entity.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kkkj.yorijori_be.Entity.BaseTimeEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_view_log")
@Entity
public class UserViewLogEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "view_log_id")
    private Long userviewlogid;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_token_id")
    private UserEntity userId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "view_recipe_id")
    private RecipeEntity recipeId;

    @Column(name = "scope", length = 4)
    private int scope;

    public void setUser(UserEntity user) {
        this.userId = user;
    }

    public void setRecipe(RecipeEntity recipe) {
        this.recipeId = recipe;
    }

    public void setScope(int scope){this.scope = scope;}

}
