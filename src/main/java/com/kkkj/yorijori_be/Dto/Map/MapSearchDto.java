package com.kkkj.yorijori_be.Dto.Map;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MapSearchDto {

    private String title;
    private String address;
    private String category;
    private String roadAddress;
    private String mapx;
    private String mapy;


    public MapSearchDto toDto(String title, String address, String loadAddress, String mapx, String mapy){

        MapSearchDto mapSearchDto = new MapSearchDto();

        mapSearchDto.setTitle(title);
        mapSearchDto.setAddress(address);
        mapSearchDto.setRoadAddress(loadAddress);
        mapSearchDto.setMapx(mapx);
        mapSearchDto.setMapy(mapy);

        return mapSearchDto;
    }

}