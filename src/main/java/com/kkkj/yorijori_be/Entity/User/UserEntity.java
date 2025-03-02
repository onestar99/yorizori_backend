package com.kkkj.yorijori_be.Entity.User;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kkkj.yorijori_be.Entity.BaseTimeEntity;
import com.kkkj.yorijori_be.Entity.Log.UserSearchedIngredientEntity;
import com.kkkj.yorijori_be.Entity.Log.UserSearchedRecipeEntity;
import com.kkkj.yorijori_be.Entity.Log.UserViewLogEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import com.kkkj.yorijori_be.Entity.Tip.TipInfoEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User")
@Entity
public class UserEntity extends BaseTimeEntity {

    @Id
    @Column(name = "user_token_id")
    private String userTokenId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "image_address")
    private String imageAddress;

    @Column(name = "age")
    private String age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "oauth_division")
    private String oauthDivision;


    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserCommentEntity> Comments;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserTipCommentEntity> TipComments;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RecipeEntity> Recipes;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TipEntity> tips;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TipInfoEntity> tipsInfo;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserViewLogEntity> ViewLog;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserSearchedRecipeEntity> SearchedRecipe;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserSearchedIngredientEntity> SearchedIngredient;

    // 유저 프로필 이미지 업데이트
    public void updateProfile(String profileAddress){
        this.imageAddress = profileAddress;
    }

    // 유저 프로필 닉네임 업데이트
    public void updateNickName(String nickname) {
        this.nickname = nickname;
    }

    // 유저 프로필 성별 업데이트
    public void updateGender(String gender) {
        this.gender = gender;
    }
    
    // 유저 프로필 나이 업데이트
    public void updateAge(String age) {
        this.age = age;
    }

}
