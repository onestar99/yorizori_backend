package com.kkkj.yorijori_be.Dto.Tip;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeDetailEntity;
import com.kkkj.yorijori_be.Entity.Tip.TipDetailEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class TipOrderDto {

    private String contents;
    private String img;


    // TipDetailsDto에 포함시키기 위한 Order List이다.

    public static TipOrderDto toDto(TipDetailEntity tipDetailEntity){

        TipOrderDto tipOrderDto = TipOrderDto.builder()
                .contents(tipDetailEntity.getTipDetail())
                .img(tipDetailEntity.getTipImage())
                .build();
        return tipOrderDto;
    }

}
