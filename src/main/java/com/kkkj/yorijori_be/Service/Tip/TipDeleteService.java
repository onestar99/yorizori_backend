package com.kkkj.yorijori_be.Service.Tip;

import com.kkkj.yorijori_be.Dto.Tip.TipDto;
import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Entity.User.UserTipCommentEntity;
import com.kkkj.yorijori_be.Repository.Tip.TipInfoRepository;
import com.kkkj.yorijori_be.Repository.Tip.TipRepository;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import com.kkkj.yorijori_be.Repository.User.UserTipCommentRepository;
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
    private final UserTipCommentRepository userTipCommentRepository;
    private final TipInfoRepository tipInfoRepository;

    @Transactional
    public void deleteTipByTipId(Long tokenId){
        //id에 해당하는 tip 찾기
        TipEntity tipEntity = tipRepository.findByTipId(tokenId);
        //tip 삭제
        tipRepository.delete(tipEntity);
        //tip에 해당하는 tipinfo 삭제
        tipInfoRepository.deleteAllInBatch();
        tipInfoRepository.flush();
        //tip에 해당하는 tipcomment 삭제
        List<UserTipCommentEntity> userTipCommentEntityList = userTipCommentRepository.findByBoard(tipEntity);
        for(UserTipCommentEntity userTipComment : userTipCommentEntityList){
            userTipCommentRepository.delete(userTipComment);
        }
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
