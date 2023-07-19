package com.kkkj.yorijori_be.Controller.Tip;

import com.kkkj.yorijori_be.Service.Tip.TipSaveUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tip/update")
public class TipUpdateController {

    private final TipSaveUpdateService tipSaveUpdateService;


    // 토큰 아이디를 통한 닉네임 수정
    // return 변경된 닉네임
    @GetMapping("") @ResponseBody
    public String updateTitle(
            @RequestParam(value = "tokenId", required = true) String tokenId,
            @RequestParam(value = "title", required = true) String Title
    ){
        tipSaveUpdateService.updateTitleById(tokenId,Title);
        return Title;
    }
}
