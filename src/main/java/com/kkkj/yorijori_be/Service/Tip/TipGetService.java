package com.kkkj.yorijori_be.Service.Tip;

import com.kkkj.yorijori_be.Dto.Recipe.RecipeDetailDto;
import com.kkkj.yorijori_be.Dto.Tip.TipDetailDto;
import com.kkkj.yorijori_be.Dto.Tip.TipListDto;
import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Repository.Tip.TipRepository;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TipGetService {

    private final TipRepository tipRepository;
    private final UserRepository userRepository;

    public Page<TipEntity> getTipPaging(int pageNo, int pageSize, String sortBy){

        // 페이지 인스턴스 생성
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return tipRepository.findAll(pageable);

    }

    public List<TipEntity> getTips(String userTokenId){
        UserEntity user = userRepository.findByUserTokenId(userTokenId);
        return user.getTips();
    }

    public List<TipListDto> getTipsPart(){
        List<TipEntity> tipEntityList = tipRepository.findAll();
        List<TipListDto> tipListDtoList = new ArrayList<>();
        for(TipEntity tipEntity : tipEntityList){
            tipListDtoList.add(TipListDto.toDto(tipEntity));
        }
        return tipListDtoList;
    }

    public List<TipDetailDto> getTipDetailById(String userTokenId){
        UserEntity user = userRepository.findByUserTokenId(userTokenId);
        List<TipDetailDto> tipDetailDtos = new ArrayList<>();
        for (TipEntity tipEntity : user.getTips()){
            tipDetailDtos.add(TipDetailDto.toDto(tipEntity));
        }
        return tipDetailDtos;
    }


}
