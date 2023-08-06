package com.kkkj.yorijori_be.Dto.Tip;

import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
public class TipDetailDto {
    private Long tipId;
    private String tipThumbnail;
    private String tipTitle;
    private String userProfileImg;
    private String userNickName;
    private String contents;
    private LocalDateTime date;
    private int heartCount;
    private int viewCount;
    private int reviewCount;


    public static TipDetailDto toDto(TipEntity tipEntity){
        TipDetailDto tipDetailDto = TipDetailDto.builder()
                .tipId(tipEntity.getTipId())
                .tipTitle(tipEntity.getTipTitle())
                .heartCount(tipEntity.getTipHits())
                .contents(tipEntity.getTipDetail())
                .tipThumbnail(tipEntity.getTipThumbnail())
                .userNickName(tipEntity.getUser().getNickname())
                .userProfileImg(tipEntity.getUser().getImageAddress())
                .viewCount(builder().viewCount)
                .date(tipEntity.getCreatedTime())
                .reviewCount(builder().reviewCount)
                .build();
        return tipDetailDto;
    }

}
