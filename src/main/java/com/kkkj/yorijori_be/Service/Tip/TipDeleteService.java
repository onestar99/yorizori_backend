package com.kkkj.yorijori_be.Service.Tip;

import com.kkkj.yorijori_be.Dto.Tip.TipDto;
import com.kkkj.yorijori_be.Repository.Tip.TipRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TipDeleteService {
    private final TipRepository tipRepository;

    @Transactional
    public void deleteTipById(String tokenId){
        TipDto tipDto = TipDto.toTipDto(tipRepository.findById(tokenId).get());
        tipRepository.delete(tipDto.toEntity());
    }
}
