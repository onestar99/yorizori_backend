package com.kkkj.yorijori_be.Entity.User;


import com.kkkj.yorijori_be.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User")
@Entity
public class UserEntity extends BaseTimeEntity {

    @Id
    private String UserTokenId;

    @Column
    private String NickName;

    @Column
    private String ImageAddress;

    @Column
    private String Age;

    @Column
    private String Gender;

    @Column
    private String OAuthDivision;


//    @ManyToOne
//    @JoinColumn(name = "user_token_id")
//    private UserCommentEntity userCommentEntity;


}
