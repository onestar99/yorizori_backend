package com.kkkj.yorijori_be.Dto.Tip;

import com.kkkj.yorijori_be.Entity.Tip.TipDetailEntity;
import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TipDetailDto {
    private String tipDetail;
    private String tipImage;


    public TipDetailEntity toEntity(){
        TipDetailEntity build = TipDetailEntity.builder()
                .tipDetail(tipDetail)
                .tipImage(tipImage)
                .build();
        return build;
    }

}
