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
@Table(name = "user_comment")
@Entity
public class UserCommentEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_token_id")
    private UserEntity user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "board_id")
    private RecipeEntity board;

    @Column(name = "comment")
    private String comment;

    @Column(name = "scope", length = 4)
    private Integer scope;

    // userTokenId setting
    public void setUser(UserEntity user) {
        this.user = user;
    }

    // board setting
    public void setBoard(RecipeEntity recipe) {
        this.board = recipe;
    }



}
