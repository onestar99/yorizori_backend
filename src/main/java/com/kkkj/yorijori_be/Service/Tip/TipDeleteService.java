package com.kkkj.yorijori_be.Service.Tip;

import com.kkkj.yorijori_be.Dto.Tip.TipDto;
import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import com.kkkj.yorijori_be.Entity.User.UserCommentEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Repository.Tip.TipRepository;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TipDeleteService {
    private final TipRepository tipRepository;
    private final UserRepository userRepository;

    @Transactional
    public void deleteTipById(String tokenId){
        TipDto tipDto = TipDto.toTipDto(tipRepository.findById(tokenId).get());
        tipRepository.delete(tipDto.toEntity());
    }

    @Transactional
    public boolean deleteAllTipById(String tokenId){
        UserEntity user = userRepository.findByUserTokenId(tokenId);
        if (user != null){
            List<TipEntity> tips = user.getTips();
            tipRepository.deleteAllInBatch(tips);
            tipRepository.flush();
            return true;
        } else {
            return false;
        }

    }
}
