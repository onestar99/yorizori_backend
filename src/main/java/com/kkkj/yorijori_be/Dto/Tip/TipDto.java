package com.kkkj.yorijori_be.Dto.Tip;

import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TipDto {
    private String tipTitle;
    private int tipHits = 0;
    private String tipDetail;
    private String tipThumbnail;

    public TipEntity toEntity(){
        TipEntity build = TipEntity.builder()
                .tipTitle(tipTitle)
                .tipHits(tipHits)
                .tipDetail(tipDetail)
                .tipThumbnail(tipThumbnail)
                .build();

        return build;
    }
}
