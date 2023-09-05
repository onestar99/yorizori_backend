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
    private int tipViewCount = 0;
    private int tipHeartCount = 0;
    private int tipReviewCount = 0;
    private String tipThumbnail;
    private String tipDetail;

    public TipEntity toEntity(){
        TipEntity build = TipEntity.builder()
                .tipTitle(tipTitle)
                .tipViewCount(tipViewCount)
                .tipHeartCount(tipHeartCount)
                .tipReviewCount(tipReviewCount)
                .tipThumbnail(tipThumbnail)
                .tipDetail(tipDetail)
                .build();

        return build;
    }

    public static TipDto toTipDto(TipEntity tipEntity){
        TipDto tipDto = new TipDto();
        tipDto.setTipHeartCount(tipEntity.getTipHeartCount());
        tipDto.setTipViewCount(tipEntity.getTipViewCount());
        tipDto.setTipReviewCount(tipEntity.getTipReviewCount());
        tipDto.setTipThumbnail(tipEntity.getTipThumbnail());
        tipDto.setTipTitle(tipEntity.getTipTitle());
        tipDto.setTipDetail(tipEntity.getTipDetail());
        return tipDto;
    }
}
