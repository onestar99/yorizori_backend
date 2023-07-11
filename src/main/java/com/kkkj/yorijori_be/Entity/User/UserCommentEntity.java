package com.kkkj.yorijori_be.Entity.User;

import com.kkkj.yorijori_be.Entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "UserComment")
@Entity
public class UserCommentEntity extends BaseTimeEntity {

    @Id
    @Column
    private int CommentId;

    @Column
    private String UserTokenId;

    @Column
    private String Comment;

    @Column(length = 4)
    private String Scope;



}
