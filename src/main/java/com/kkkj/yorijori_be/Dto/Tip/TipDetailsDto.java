package com.kkkj.yorijori_be.Dto.Tip;

import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
public class TipDetailsDto {
    private Long id;
    private String thumbnail;
    private String title;
    private String profileImg;
    private String nickname;
    private LocalDateTime date;
    private int heartCount;
    private int viewCount;
    private int reviewCount;
    private List<TipOrderDto> order;
    public static TipDetailsDto toDto(TipEntity tipEntity, List<TipOrderDto> order){


        TipDetailsDto tipDetailsDto = TipDetailsDto.builder()
                .id(tipEntity.getTipId())
                .thumbnail(tipEntity.getTipThumbnail())
                .title(tipEntity.getTipTitle())
                .profileImg(tipEntity.getUser().getImageAddress())
                .nickname(tipEntity.getUser().getNickname())
                .date(tipEntity.getCreatedTime())
                .heartCount(tipEntity.getTipHits())
                .viewCount(tipEntity.getTipScope())
                .reviewCount(tipEntity.getTipReviewCount())
                .order(order)
                .build();

        return tipDetailsDto;

    }

}
