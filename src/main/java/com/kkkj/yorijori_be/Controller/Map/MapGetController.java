package com.kkkj.yorijori_be.Controller.Map;

import com.kkkj.yorijori_be.Dto.Map.MapLocationDto;
import com.kkkj.yorijori_be.Dto.Map.MapSearchDto;
import com.kkkj.yorijori_be.Service.Map.MapGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MapGetController {

    private final MapGetService mapGetService;

    @ResponseBody
    @GetMapping(value = "/map/{query}")
    public List<MapSearchDto> getStoreBySearchQuery(@PathVariable String query){
        return mapGetService.getSearchResult(query);
    }


    @ResponseBody
    @PostMapping("/get/api")
    public List<MapSearchDto> getLocation(@RequestBody MapLocationDto mapLocationDto){

        System.out.println(mapLocationDto.getLatitude());
        System.out.println(mapLocationDto.getLongitude());
        String location = mapGetService.getLocation(mapLocationDto);
        return mapGetService.getSearchResult(location + " " + mapLocationDto.getFoodName());

    }

}
