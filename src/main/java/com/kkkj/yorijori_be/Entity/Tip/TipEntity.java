package com.kkkj.yorijori_be.Entity.Tip;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kkkj.yorijori_be.Entity.BaseTimeEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Tip")
@Entity
public class TipEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tip_id")
    private Long tipId;

    @Column(name = "tip_title")
    private String tipTitle;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_token_id")
    private UserEntity user;

    @Column(name = "tip_hits", nullable = false)
    private int tipHits;

    @Column(name = "tip_detail")
    private String tipDetail;

    @Column(name = "tip_thumbnail")
    private String tipThumbnail;

    // userTokenId setting
    public void setUser(UserEntity user) {
        this.user = user;
    }
}
