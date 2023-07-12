package com.kkkj.yorijori_be.Entity.User;

import com.kkkj.yorijori_be.Entity.BaseTimeEntity;
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

    @ManyToOne
    @JoinColumn(name = "user_token_id")
    private UserEntity user;

    @Column(name = "comment")
    private String comment;

    @Column(name = "scope", length = 4)
    private String scope;


    // userTokenId setting
    public void setUser(UserEntity user) {
        this.user = user;
    }



}
