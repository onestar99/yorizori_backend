package com.kkkj.yorijori_be.Service.Tip;

import com.kkkj.yorijori_be.Dto.Tip.TipInfoDto;
import com.kkkj.yorijori_be.Dto.Tip.TipListDto;
import com.kkkj.yorijori_be.Dto.Tip.TipPostDto;
import com.kkkj.yorijori_be.Dto.Tip.TipReviewDto;
import com.kkkj.yorijori_be.Dto.User.UserCommentDto;
import com.kkkj.yorijori_be.Dto.User.UserTipCommentDto;
import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import com.kkkj.yorijori_be.Entity.Tip.TipInfoEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Entity.User.UserTipCommentEntity;
import com.kkkj.yorijori_be.Repository.Tip.TipInfoRepository;
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
    private final TipInfoRepository tipInfoRepository;

    public List<TipListDto> getTipsPart(){
        List<TipEntity> tipEntityList = tipRepository.findAll();
        List<TipListDto> tipListDtoList = new ArrayList<>();
        for(TipEntity tipEntity : tipEntityList){
            tipListDtoList.add(TipListDto.toDto(tipEntity));
        }
        return tipListDtoList;
    }

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

    public TipInfoDto getTipIsHeart(Long tipId,String userId){
        TipEntity tipEntity = tipRepository.findByTipId(tipId);
        UserEntity userEntity = userRepository.findByUserTokenId(userId);
        List<TipInfoEntity> tipInfoEntity = tipInfoRepository.findByTipAndUser(tipEntity,userEntity);
        TipInfoDto tipInfoDto = new TipInfoDto();
        if(tipInfoEntity.isEmpty()){
            tipInfoDto.setHeart(false);
            tipInfoDto.setTipHeartCount(tipEntity.getTipHeartCount());
        }else{
            tipInfoDto.setHeart(tipInfoEntity.get(0).getIsHeart());
            tipInfoDto.setTipHeartCount(tipEntity.getTipHeartCount());
        }
        return tipInfoDto;
    }

    public TipPostDto getTipPost(Long tipId){
        TipEntity tipEntity = tipRepository.findByTipId(tipId);
        TipPostDto tipPostDto = new TipPostDto();
        tipPostDto.setTipDetail(tipEntity.getTipDetail());
        tipPostDto.setTipTitle(tipEntity.getTipTitle());
        tipPostDto.setTipThumbnail(tipEntity.getTipThumbnail());
        tipPostDto.setUserId(tipEntity.getUser().getUserTokenId());
        return tipPostDto;
    }

}
