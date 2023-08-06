package com.kkkj.yorijori_be.Controller.Tip;

import com.kkkj.yorijori_be.Dto.Tip.TipDetailDto;
import com.kkkj.yorijori_be.Dto.Tip.TipListDto;
import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import com.kkkj.yorijori_be.Repository.Tip.TipRepository;
import com.kkkj.yorijori_be.Service.Tip.TipGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tip/get")
public class TipGetController {

    private final TipGetService tipGetService;
    private final TipRepository tipRepository;

    @GetMapping("/all/paging") @ResponseBody
    public Page<TipEntity> getAllPaging(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "user_token_id", required = false) String sortBy
    ){
        return tipGetService.getTipPaging(pageNo, pageSize, sortBy);
    }

    @GetMapping("/all") @ResponseBody
    public Page<TipListDto> getTipAll(
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "12", required = false) int pageSize
            ){
        List<TipListDto> tipListDtoList= tipGetService.getTipsPart();
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()),tipListDtoList.size());
        Page<TipListDto> tipListDtos = new PageImpl<>(tipListDtoList.subList(start,end),pageRequest,tipListDtoList.size());
        return tipListDtos;
    }

    @GetMapping("/part") @ResponseBody
    public Page<TipListDto> getTipPartall(
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "8", required = false) int pageSize
    ){
        List<TipListDto> tipListDtoList= tipGetService.getTipsPart();
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()),tipListDtoList.size());
        Page<TipListDto> tipListDtos = new PageImpl<>(tipListDtoList.subList(start,end),pageRequest,tipListDtoList.size());
        return tipListDtos;
    }

    @GetMapping("/details/{userTokenId}") @ResponseBody
    public Page<TipDetailDto> getTipDetailsById(
            @PathVariable String userTokenId,
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "8", required = false) int pageSize
    ){
        List<TipDetailDto> tipDetailDtos= tipGetService.getTipDetailById(userTokenId);
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()),tipDetailDtos.size());
        Page<TipDetailDto> tipDetailDtosPage = new PageImpl<>(tipDetailDtos.subList(start,end),pageRequest,tipDetailDtos.size());
        return tipDetailDtosPage;
    }

}