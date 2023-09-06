package com.kkkj.yorijori_be.Dto.Tip;

import com.kkkj.yorijori_be.Dto.User.UserTipCommentDto;
import com.kkkj.yorijori_be.Entity.User.UserTipCommentEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TipReviewDto {
    private int reviewCount;
    private List<UserTipCommentDto> reviews;
    private Boolean isHeart;

}
