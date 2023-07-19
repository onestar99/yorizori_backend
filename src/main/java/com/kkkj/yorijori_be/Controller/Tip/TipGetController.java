package com.kkkj.yorijori_be.Controller.Tip;

import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import com.kkkj.yorijori_be.Repository.Tip.TipRepository;
import com.kkkj.yorijori_be.Service.Tip.TipGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
}