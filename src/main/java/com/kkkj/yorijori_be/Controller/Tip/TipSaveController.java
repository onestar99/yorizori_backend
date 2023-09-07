package com.kkkj.yorijori_be.Controller.Tip;

import com.kkkj.yorijori_be.Dto.Tip.TipDto;
import com.kkkj.yorijori_be.Dto.Tip.TipPostDto;
import com.kkkj.yorijori_be.Service.Tip.TipSaveUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tip/save")
public class TipSaveController {
    private final TipSaveUpdateService tipSaveUpdateService;

    @PostMapping("/{userTokenId}")
    public ResponseEntity saveTipInfo(@PathVariable String userTokenId, @RequestBody TipDto tipDto) {
        // 인자로 userTokenId와 Dto를 넘겨서 레시피 정보를 저장해주는 함수
        tipSaveUpdateService.saveTip(userTokenId,tipDto);
        return ResponseEntity.ok("tip saved successfully : " + tipDto.getTipTitle());
    }
    @PostMapping("/details")
    @ResponseBody
    public Long saveTip(@RequestBody TipPostDto tipPostDto){

        TipDto tipDto = TipDto.tipPostDtoToDto(tipPostDto);

        long tipId = tipSaveUpdateService.saveTip(tipPostDto.getUserId(),tipDto);

        return tipId;
    }

}
