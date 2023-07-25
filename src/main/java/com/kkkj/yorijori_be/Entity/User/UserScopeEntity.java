package com.kkkj.yorijori_be.Entity.User;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kkkj.yorijori_be.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "user_scope")
public class UserScopeEntity extends BaseTimeEntity {


    @Id
    @Column(name = "scope_id")
    private int scopeId;

    @Column(name = "recipe_id")
    private String recipeId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_token_id")
    private UserEntity user;

    @Column(name = "scope", length = 4)
    private String scope;


}
