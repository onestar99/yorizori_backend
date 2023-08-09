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
    private int tipScope = 0;
    private int tipReviewCount = 0;
    private String tipThumbnail;

    public TipEntity toEntity(){
        TipEntity build = TipEntity.builder()
                .tipTitle(tipTitle)
                .tipHits(tipHits)
                .tipHits(tipHits)
                .tipScope(tipScope)
                .tipReviewCount(tipReviewCount)
                .tipThumbnail(tipThumbnail)
                .build();

        return build;
    }

    public static TipDto toTipDto(TipEntity tipEntity){
        TipDto tipDto = new TipDto();
        tipDto.setTipScope(tipEntity.getTipScope());
        tipDto.setTipHits(tipEntity.getTipHits());
        tipDto.setTipReviewCount(tipEntity.getTipReviewCount());
        tipDto.setTipThumbnail(tipEntity.getTipThumbnail());
        tipDto.setTipTitle(tipEntity.getTipTitle());
        return tipDto;
    }
}
