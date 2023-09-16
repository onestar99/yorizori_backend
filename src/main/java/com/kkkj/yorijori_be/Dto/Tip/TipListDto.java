package com.kkkj.yorijori_be.Dto.Tip;

import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Builder
@Getter
@Setter
@ToString
public class TipListDto {
    private Long tipId;
    private String tipTitle;
    private int tipHeartCount;
    private int tipViewCount;
    private String tipThumbnail;
    private String nickname;
    private String profileImg;
    private String date;
    private String tipDetail;

    public static TipListDto toDto(TipEntity tipEntity){
        TipListDto tipListDto = TipListDto.builder()
                .tipId(tipEntity.getTipId())
                .tipTitle(tipEntity.getTipTitle())
                .tipDetail(tipEntity.getTipDetail())
                .tipHeartCount(tipEntity.getTipHeartCount())
                .tipThumbnail(tipEntity.getTipThumbnail())
                .nickname(tipEntity.getUser().getNickname())
                .profileImg(tipEntity.getUser().getImageAddress())
                .tipViewCount(tipEntity.getTipViewCount())
                .date(tipEntity.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
        return tipListDto;
    }

    public static Page<TipListDto> toDtoPage(Page<TipEntity> tipEntityPage){
        Page<TipListDto> tipListDtoPage = tipEntityPage.map(m -> TipListDto.builder()
                .tipId(m.getTipId())
                .tipViewCount(m.getTipViewCount())
                .tipHeartCount(m.getTipHeartCount())
                .tipThumbnail(m.getTipThumbnail())
                .nickname(m.getUser().getNickname())
                .profileImg(m.getUser().getImageAddress())
                .tipTitle(m.getTipTitle())
                .build());
        return tipListDtoPage;

    }

}
