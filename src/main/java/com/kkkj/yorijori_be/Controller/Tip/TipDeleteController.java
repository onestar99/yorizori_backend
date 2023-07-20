package com.kkkj.yorijori_be.Controller.Tip;

import com.kkkj.yorijori_be.Service.Tip.TipDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tip/delete")
public class TipDeleteController {
    private final TipDeleteService tipDeleteService;

//    @GetMapping("")
//    @ResponseBody
//    public String deleteTip(
//            @RequestParam(value = "tokenId", required = true) String tokenId
//    ){
//        tipDeleteService.deleteTipById(tokenId);
//        return "deleted";
//    }

    @GetMapping("/allTips") @ResponseBody
    public String deleteUserAllTips(
            @RequestParam(value = "tokenId", required = true) String tokenId
    ){
//        userDeleteService.deleteAllCommentById(tokenId);
        tipDeleteService.deleteAllTipById(tokenId);
        return "deleted";
    }
}
