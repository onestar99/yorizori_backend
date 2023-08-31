package com.kkkj.yorijori_be.Service.Tip;

import com.kkkj.yorijori_be.Dto.Recipe.RecipeDetailDto;
import com.kkkj.yorijori_be.Dto.Recipe.RecipeListDto;
import com.kkkj.yorijori_be.Dto.Tip.TipDetailDto;
import com.kkkj.yorijori_be.Dto.Tip.TipDetailsDto;
import com.kkkj.yorijori_be.Dto.Tip.TipListDto;
import com.kkkj.yorijori_be.Dto.Tip.TipOrderDto;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.Tip.TipDetailEntity;
import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Repository.Tip.TipRepository;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TipGetService {

    private final TipRepository tipRepository;
    private final UserRepository userRepository;





    public List<TipListDto> getTipsPart(){
        List<TipEntity> tipEntityList = tipRepository.findAll();
        List<TipListDto> tipListDtoList = new ArrayList<>();
        for(TipEntity tipEntity : tipEntityList){
            tipListDtoList.add(TipListDto.toDto(tipEntity));
        }
        return tipListDtoList;
    }

    public TipDetailsDto getTipDetailByTipId(Long tipId){
        TipEntity tipEntity = tipRepository.findByTipId(tipId);

        List<TipDetailEntity> tipDetailEntityList = tipEntity.getDetails();
        List<TipOrderDto> tipOrderDtoList = new ArrayList<>();
        for(TipDetailEntity tipDetailEntity: tipDetailEntityList){
            tipOrderDtoList.add(TipOrderDto.toDto(tipDetailEntity));
        }

        TipDetailsDto tipDetailsDto = TipDetailsDto.toDto(tipEntity,tipOrderDtoList);

        return tipDetailsDto;
    }

    public Page<TipListDto> getTipsPagingByUserId(int pageNo, int pageSize,String userId){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdTime").descending());
        Page<TipEntity> tipEntityPage = tipRepository.findAllByUser_UserTokenId(userId,pageable);
        Page<TipListDto> tipListDtoPage = TipListDto.toDtoPage(tipEntityPage);
        return tipListDtoPage;
    }




}
