package com.kkkj.yorijori_be.Entity.User;


import com.kkkj.yorijori_be.Entity.BaseTimeEntity;
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


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserCommentEntity> Comments;


}
