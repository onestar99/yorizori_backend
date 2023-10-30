package com.kkkj.yorijori_be.Controller.Tip;

import com.kkkj.yorijori_be.Dto.Recipe.RecipeListDto;
import com.kkkj.yorijori_be.Dto.Tip.TipListDto;
import com.kkkj.yorijori_be.Dto.Tip.TipPostDto;
import com.kkkj.yorijori_be.Dto.Tip.TipReviewDto;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import com.kkkj.yorijori_be.Repository.Tip.TipRepository;
import com.kkkj.yorijori_be.Service.Tip.TipGetService;
import com.kkkj.yorijori_be.Service.Tip.TipSaveUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tip/get")
public class TipGetController {

    private final TipGetService tipGetService;
    private final TipSaveUpdateService tipSaveUpdateService;
    private final TipRepository tipRepository;

    //검색과 나열 동시 진행
    @GetMapping("/all") @ResponseBody
    public Page<TipListDto> getTipAll(
            @RequestParam(value = "search") String search,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "orderBy") String orderBy
    ){
        if(search.equals(null)){
            // 페이지 사이즈 고정
            int pageSize = 12;
            List<TipListDto> tipListDtoList= tipGetService.getTipsPart();
            PageRequest pageRequest = PageRequest.of(pageNo,pageSize);
            int start = (int) pageRequest.getOffset();
            int end = Math.min((start + pageRequest.getPageSize()),tipListDtoList.size());
            Page<TipListDto> tipListDtos = new PageImpl<>(tipListDtoList.subList(start,end),pageRequest,tipListDtoList.size());
            return tipListDtos;
        }else{
            return tipGetService.tipSearchList(search,pageNo,orderBy);
        }
    }

    @GetMapping("/edit/{tipId}") @ResponseBody
    public TipPostDto getTipPost(@PathVariable Long tipId){
        return tipGetService.getTipPost(tipId);
    }

    @GetMapping("/part") @ResponseBody
    public List<TipListDto> getTipPartall(){
        List<TipListDto> tipListDtos = tipGetService.getTipsPart();
        if(tipListDtos.size()>=8){
            tipListDtos = tipGetService.getTipsPart().subList(0,8);
        }
        return tipListDtos;
    }

    @GetMapping("/details") @ResponseBody
    public TipListDto getTipDetails(
            @RequestParam(value = "tipId",required = false) String tipId,
            @RequestParam(value = "userId",required = false) String userId){

        // 팁이 숫자가 아니면 null 반환
        long longTipId;
        try {
            longTipId = Long.parseLong(tipId);
        } catch (Exception e){
            return null;
        }

        TipEntity tip = tipRepository.findByTipId(longTipId);
        // 팁이 존재하지 않으면 null 반환
        if (tip == null) {
            return null;
        }

        //조회수 1 올리기
        tipSaveUpdateService.updateViewCount(longTipId);
        return tipGetService.getTipDetail(longTipId, userId);
    }



    @GetMapping("/reviews/{tipId}") @ResponseBody
    public TipReviewDto getTipReviews(
            @PathVariable Long tipId
    ){
        return tipGetService.getTipReviews(tipId);
    }



//    @GetMapping("/details") @ResponseBody
//    public TipDetailsDto getTipDetailsById(
//            @RequestParam(value = "tipId", required = false) Long tipId
//    ){
//        TipDetailsDto tipDetailsDto = tipGetService.getTipDetailByTipId(tipId);
//        return tipDetailsDto;
//
//
//    }

//    // 검색
//    @ResponseBody
//    @GetMapping("/all")
//    public Page<TipListDto> getTipTitleSearchedPaging(
//            @RequestParam(value = "search") String search,
//            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
//            @RequestParam(value = "orderBy") String orderBy){
//        return tipGetService.tipSearchList(search,pageNo,orderBy);
//    }


}