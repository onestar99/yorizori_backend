package com.kkkj.yorijori_be.Dto.Tip;

import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class TipListDto {
    private Long tipId;
    private String tipTitle;
    private int tipHits = 0;
    private String tipDetail;
    private String tipThumbnail;
    private String userNickName;
    private String userProfileImg;

    public static TipListDto toDto(TipEntity tipEntity){
        TipListDto tipListDto = TipListDto.builder()
                .tipId(tipEntity.getTipId())
                .tipTitle(tipEntity.getTipTitle())
                .tipHits(tipEntity.getTipHits())
                .tipDetail(tipEntity.getTipDetail())
                .tipThumbnail(tipEntity.getTipThumbnail())
                .userNickName(tipEntity.getUser().getNickname())
                .userProfileImg(tipEntity.getUser().getImageAddress())
                .build();
        return tipListDto;
    }

}
