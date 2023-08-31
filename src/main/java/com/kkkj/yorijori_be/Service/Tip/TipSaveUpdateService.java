package com.kkkj.yorijori_be.Service.Tip;

import com.kkkj.yorijori_be.Dto.Recipe.RecipeDetailDto;
import com.kkkj.yorijori_be.Dto.Recipe.RecipeDto;
import com.kkkj.yorijori_be.Dto.Recipe.RecipePostDto;
import com.kkkj.yorijori_be.Dto.Tip.TipDetailDto;
import com.kkkj.yorijori_be.Dto.Tip.TipDto;
import com.kkkj.yorijori_be.Dto.Tip.TipPostDto;
import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeDetailEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.Tip.TipDetailEntity;
import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Repository.Tip.TipRepository;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TipSaveUpdateService {
    private final TipRepository tipRepository;
    private final UserRepository userRepository;


    public void saveTip(String userTokenId, TipDto tipDto){

        // TokenId를 통해 유저 정보 찾기
        UserEntity userEntity = userRepository.findByUserTokenId(userTokenId);
        // 전달받은 DTO를 Entity로 변경
        TipEntity tipEntity = tipDto.toEntity();
        // tip Entity 유저 정보 세팅
        tipEntity.setUser(userEntity);
        // User에 tip Entity 추가
        userEntity.getTips().add(tipEntity);

        System.out.println(userEntity.getTips().get(0).getTipTitle());
        System.out.println(userEntity.getUserTokenId());


        // 저장
        userRepository.save(userEntity);

    }

    @Transactional
    public void updateTitleById(String tokenId, String Title){
        TipDto tipDto = TipDto.toTipDto(tipRepository.findById(tokenId).get());
        tipDto.setTipTitle(Title);
        tipRepository.save(tipDto.toEntity());
    }

    public void saveTipDetails(TipPostDto tipPostDto){
        tipPostDto.getTipId();

        // TokenId를 통해 유저 정보 찾기
        TipEntity tipEntity = tipRepository.findByTipId(tipPostDto.getTipId());

        for(int i = 0; i < tipPostDto.getTipDetailDtoList().size(); i++){
            TipDetailDto tipDetailDto = tipPostDto.getTipDetailDtoList().get(i);
            TipDetailEntity tipDetailEntity = tipDetailDto.toEntity();
            tipDetailEntity.setOrder(i+1);
            tipDetailEntity.setTip(tipEntity);
            tipEntity.getDetails().add(tipDetailEntity);
            tipRepository.save(tipEntity);
        }

    }



}
