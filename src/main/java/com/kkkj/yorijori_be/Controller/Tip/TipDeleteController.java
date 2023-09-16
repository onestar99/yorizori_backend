package com.kkkj.yorijori_be.Controller.Tip;

import com.kkkj.yorijori_be.Service.Tip.TipDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tip/delete")
public class TipDeleteController {
    private final TipDeleteService tipDeleteService;



    @GetMapping("/allTips") @ResponseBody
    public String deleteUserAllTips(
            @RequestParam(value = "tokenId", required = true) String tokenId
    ){
        tipDeleteService.deleteAllTipById(tokenId);
        return "deleted";
    }

    @ResponseBody
    @DeleteMapping("/all")
    public String deleteTip(
            @RequestParam(value = "tokenId", required = true) Long tokenId
    ){
        tipDeleteService.deleteTipByTipId(tokenId);
        return "deleted";
    }
}
