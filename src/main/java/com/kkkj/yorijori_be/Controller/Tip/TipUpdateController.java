package com.kkkj.yorijori_be.Controller.Tip;

import com.kkkj.yorijori_be.Service.Tip.TipSaveUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tip/update")
public class TipUpdateController {

    private final TipSaveUpdateService tipSaveUpdateService;



}
