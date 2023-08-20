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
@Table(name = "user_searched_recipe_log")
@Entity
public class UserSearchedRecipeEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "searched_Recipe_log_id")
    private Long recipeId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_token_id")
    private UserEntity userId;

    @Column(name = "searched_log")
    private String searchedlog;

    @Column(name = "scope", length = 4)
    private Integer scope;
}
