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
    private String title;
    private String thumbnail;
    private String detail;


}
