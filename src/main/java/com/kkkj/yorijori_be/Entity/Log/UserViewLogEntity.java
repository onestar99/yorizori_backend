package com.kkkj.yorijori_be.Entity.Log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_view_log")
@Entity
public class UserViewLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "view_log_id")
    private Long userviewlogid;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_token_id")
    private UserEntity user;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTime;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "view_recipe_id")
    private RecipeEntity recipe;

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setRecipe(RecipeEntity recipe) {
        this.recipe = recipe;
    }


}
