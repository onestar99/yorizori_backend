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
@Table(name = "user_searched_ingredient_log")
@Entity
public class UserSearchedIngredientEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "searched_ingredient_log_id")
    private Long ingredientId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_token_id")
    private UserEntity user;

    @Column(name = "searched_log")
    private String searchedlog;

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setSearchedlog(String searchedlog){this.searchedlog = searchedlog;}

}
