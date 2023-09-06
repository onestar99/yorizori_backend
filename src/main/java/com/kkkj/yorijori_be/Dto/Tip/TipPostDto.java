package com.kkkj.yorijori_be.Dto.Tip;

import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TipPostDto {
    private String userId;
    private String tipTitle;
    private String tipThumbnail;
    private String tipDetail;


}
