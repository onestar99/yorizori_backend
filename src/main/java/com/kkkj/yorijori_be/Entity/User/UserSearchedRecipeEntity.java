package com.kkkj.yorijori_be.Entity.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kkkj.yorijori_be.Entity.BaseTimeEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_searched_recipe_log")
@Entity
public class UserSearchedRecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "searched_Recipe_log_id")
    private Long recipeId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_token_id")
    private UserEntity user;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTime;


    @Column(name = "searched_log")
    private String searchedlog;

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setSearchedlog(String searchedlog){this.searchedlog = searchedlog;}

}
