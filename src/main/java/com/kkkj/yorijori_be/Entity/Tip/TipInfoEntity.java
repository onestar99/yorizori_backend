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
@Table(name = "tipinfo")
@Entity
public class TipInfoEntity extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tip_info_id")
    private Long tipInfoId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_token_id")
    private UserEntity user;


    @Column(name = "is_heart")
    private Boolean isHeart;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tip_id")
    private TipEntity tip;

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setTips(TipEntity tip){
        this.tip = tip;
    }


}
