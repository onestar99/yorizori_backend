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
    private Long id;
    private String thumbnail;
    private String title;
    private String profileImg;
    private String nickname;
    private String contents;
    private LocalDateTime date;
    private int heartCount;
    private int viewCount;
    private int reviewCount;


    public static TipDetailDto toDto(TipEntity tipEntity){
        TipDetailDto tipDetailDto = TipDetailDto.builder()
                .id(tipEntity.getTipId())
                .title(tipEntity.getTipTitle())
                .heartCount(tipEntity.getTipHits())
                .contents(tipEntity.getTipDetail())
                .thumbnail(tipEntity.getTipThumbnail())
                .nickname(tipEntity.getUser().getNickname())
                .profileImg(tipEntity.getUser().getImageAddress())
                .viewCount(builder().viewCount)
                .date(tipEntity.getCreatedTime())
                .reviewCount(builder().reviewCount)
                .build();
        return tipDetailDto;
    }

}
