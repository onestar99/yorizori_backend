package com.kkkj.yorijori_be.Dto.Tip;

import com.kkkj.yorijori_be.Entity.User.UserTipCommentEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TipInfoDto {

    private boolean isHeart;
}
