package com.kkkj.yorijori_be.Dto.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MapLocationDto {

    private String latitude;
    private String longitude;
    private String foodName;
}
