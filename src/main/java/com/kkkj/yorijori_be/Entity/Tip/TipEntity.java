package com.kkkj.yorijori_be.Entity.Tip;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kkkj.yorijori_be.Entity.BaseTimeEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private int tipHits;//조회수

    @Column(name = "tip_heart_count", nullable = false)
    private int tipHeartCount;//좋아요 수(하트 수)

    @Column(name = "tip_reviewCount", nullable = false)
    private int tipReviewCount;//댓글 수


    @Column(name = "tip_thumbnail")
    private String tipThumbnail;

    @Column(name = "tip_detail",columnDefinition = "TEXT")
    private String tipDetail;

    // userTokenId setting
    public void setUser(UserEntity user) {
        this.user = user;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "tip", cascade = CascadeType.ALL)
    private List<TipDetailEntity> details;
}
