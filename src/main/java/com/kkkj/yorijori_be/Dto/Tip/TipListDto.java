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
    private Long id;
    private String title;
    private int heartCount;
    private int viewCount;
    private String thumbnail;
    private String nickname;
    private String profileImg;

    public static TipListDto toDto(TipEntity tipEntity){
        TipListDto tipListDto = TipListDto.builder()
                .id(tipEntity.getTipId())
                .title(tipEntity.getTipTitle())
                .heartCount(tipEntity.getTipHits())
                .thumbnail(tipEntity.getTipThumbnail())
                .nickname(tipEntity.getUser().getNickname())
                .profileImg(tipEntity.getUser().getImageAddress())
                .viewCount(builder().viewCount)
                .build();
        return tipListDto;
    }

}
