package com.kkkj.yorijori_be.Service.Tip;

import com.kkkj.yorijori_be.Dto.Tip.TipListDto;
import com.kkkj.yorijori_be.Dto.Tip.TipReviewDto;
import com.kkkj.yorijori_be.Dto.User.UserCommentDto;
import com.kkkj.yorijori_be.Dto.User.UserTipCommentDto;
import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Entity.User.UserTipCommentEntity;
import com.kkkj.yorijori_be.Repository.Tip.TipRepository;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import com.kkkj.yorijori_be.Repository.User.UserTipCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
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
    private final UserTipCommentRepository userTipCommentRepository;

    public List<TipListDto> getTipsPart(){
        List<TipEntity> tipEntityList = tipRepository.findAll();
        List<TipListDto> tipListDtoList = new ArrayList<>();
        for(TipEntity tipEntity : tipEntityList){
            tipListDtoList.add(TipListDto.toDto(tipEntity));
        }
        return tipListDtoList;
    }

//    public TipDetailsDto getTipDetailByTipId(Long tipId){
//        TipEntity tipEntity = tipRepository.findByTipId(tipId);
//
//        List<TipDetailEntity> tipDetailEntityList = tipEntity.getDetails();
//        List<TipOrderDto> tipOrderDtoList = new ArrayList<>();
//        for(TipDetailEntity tipDetailEntity: tipDetailEntityList){
//            tipOrderDtoList.add(TipOrderDto.toDto(tipDetailEntity));
//        }
//
//        TipDetailsDto tipDetailsDto = TipDetailsDto.toDto(tipEntity,tipOrderDtoList);
//
//        return tipDetailsDto;
//    }

    public Page<TipListDto> getTipsPagingByUserId(int pageNo, int pageSize,String userId){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdTime").descending());
        Page<TipEntity> tipEntityPage = tipRepository.findAllByUser_UserTokenId(userId,pageable);
        Page<TipListDto> tipListDtoPage = TipListDto.toDtoPage(tipEntityPage);
        return tipListDtoPage;
    }


    public TipListDto getTipDetail(Long tipId, String userId) {
        TipEntity tipEntity = tipRepository.findByTipId(tipId);
        TipListDto tipListDto = TipListDto.toDto(tipEntity);
        return tipListDto;
    }

    public TipReviewDto getTipReviews(Long tipId){
        TipEntity tipEntity = tipRepository.findByTipId(tipId);
        List<UserTipCommentEntity> userTipCommentEntity = userTipCommentRepository.findByBoard(tipEntity);
        TipReviewDto tipReviewDto = new TipReviewDto();
        if(tipEntity==null){
            List<UserTipCommentDto> userTipCommentDtoList = new ArrayList<>(0);
            tipReviewDto.setReviews(userTipCommentDtoList);
            tipReviewDto.setReviewCount(0);
        }else {
            List<UserTipCommentDto> userTipCommentDtoList = new ArrayList<>();
            for (UserTipCommentEntity userTipComment : userTipCommentEntity) {
                userTipCommentDtoList.add(UserTipCommentDto.toUserTipCommentDto(userTipComment));
            }
            tipReviewDto.setReviews(userTipCommentDtoList);
            tipReviewDto.setReviewCount(tipEntity.getTipReviewCount());
        }

        return tipReviewDto;
    }

}
