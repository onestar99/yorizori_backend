package com.kkkj.yorijori_be.Service.Tip;

import com.kkkj.yorijori_be.Dto.Tip.TipDto;
import com.kkkj.yorijori_be.Dto.Tip.TipInfoDto;
import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import com.kkkj.yorijori_be.Entity.Tip.TipInfoEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Repository.Tip.TipInfoRepository;
import com.kkkj.yorijori_be.Repository.Tip.TipRepository;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TipSaveUpdateService {
    private final TipRepository tipRepository;
    private final UserRepository userRepository;
    private final TipInfoRepository tipInfoRepository;

    public long saveTip(String userTokenId, TipDto tipDto){
        // TokenId를 통해 유저 정보 찾기
        UserEntity userEntity = userRepository.findByUserTokenId(userTokenId);
        // 전달받은 DTO를 Entity로 변경
        TipEntity tipEntity = tipDto.toEntity();
        // tip Entity 유저 정보 세팅
        tipEntity.setUser(userEntity);
        // User에 tip Entity 추가
        userEntity.getTips().add(tipEntity);
        // 저장
        UserEntity user = userRepository.save(userEntity);
        int tipSize = user.getTips().size();
        long tipId = user.getTips().get(tipSize-1).getTipId();
        return tipId;
    }


    @Transactional
    public void updateTitleById(String tokenId, String Title){
        TipDto tipDto = TipDto.toTipDto(tipRepository.findById(tokenId).get());
        tipDto.setTipTitle(Title);
        tipRepository.save(tipDto.toEntity());
    }


    @Transactional
    public void updateViewCount(Long id){tipRepository.updateViewCount(id);}

    @Transactional
    public void updateReviewCount(Long id){tipRepository.updateReviewCount(id);}

    public TipInfoDto saveTipInfo(long tipId, String userId, boolean isHeart){
        UserEntity userEntity = userRepository.findByUserTokenId(userId);
        TipEntity tipEntity = tipRepository.findByTipId(tipId);
        List<TipInfoEntity> tipInfoEntitys = tipInfoRepository.findByTipAndUser(tipEntity,userEntity);
        TipInfoEntity tipInfoEntity = TipInfoEntity.builder()
                .tip(tipEntity)
                .user(userEntity)
                .isHeart(isHeart)
                .build();
        if(tipInfoEntitys.isEmpty()){
            tipInfoRepository.save(tipInfoEntity);
            System.out.println("empty");
            updatetipHeartCount(tipEntity);
        }
        else{
            tipInfoRepository.updateisHeart(isHeart,tipInfoEntitys.get(0).getTipInfoId());
            tipInfoRepository.save(tipInfoEntitys.get(0));
            if(isHeart==true&& tipInfoEntitys.get(0).getIsHeart()==false){
                tipEntity.setTipHeartCount(tipEntity.getTipHeartCount()+1);
            }else if(isHeart==false&& tipInfoEntitys.get(0).getIsHeart()==true){
                tipEntity.setTipHeartCount(tipEntity.getTipHeartCount()-1);
            }
            System.out.println(tipEntity.getTipHeartCount());
        }
        TipInfoDto tipInfoDto = new TipInfoDto();
        tipInfoDto.setHeart(isHeart);
        return tipInfoDto;
    }

    public void updatetipHeartCount(TipEntity tipEntity){
        List<TipInfoEntity> tipInfoEntities = tipInfoRepository.findByTip(tipEntity);
        for(TipInfoEntity tipInfoEntity : tipInfoEntities){
            System.out.println(tipInfoEntity.getUser().getUserTokenId());
            System.out.println(tipInfoEntity.getIsHeart());
        }
        Long iHeart = tipInfoEntities.stream().filter(t->t.getIsHeart()==true).count();
        tipEntity.setTipHeartCount(iHeart.intValue());
        tipRepository.save(tipEntity);
        System.out.println(tipEntity.getTipHeartCount());
    }
}
