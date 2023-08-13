package com.kkkj.yorijori_be.Dto.Tip;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TipPostDto {
    private Long tipId;
    private List<TipDetailDto> tipDetailDtoList;

}
