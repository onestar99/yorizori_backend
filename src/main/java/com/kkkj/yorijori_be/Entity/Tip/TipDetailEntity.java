package com.kkkj.yorijori_be.Entity.Tip;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kkkj.yorijori_be.Entity.BaseTimeEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tip_detail")
@Entity
public class TipDetailEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tip_detail_id")
    private Long tipDetailId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tip_id")
    private TipEntity tip;

    @Column(name = "tip_detail")
    private String tipDetail;

    @Column(name = "tip_image")
    private String tipImage;

    @Column(name = "order_index")
    private Integer order; // 순서

    // 팁 디테일 저장할 때 순서 설정
    public void setOrder(int orderIndex) {
        this.order = orderIndex;
    }

    // tipid setting
    public void setTip(TipEntity tip) { this.tip = tip; }


}
