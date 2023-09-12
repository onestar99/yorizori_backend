package com.kkkj.yorijori_be.Repository.Tip;

import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import com.kkkj.yorijori_be.Entity.Tip.TipInfoEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TipInfoRepository extends JpaRepository<TipInfoEntity, String> {

    List<TipInfoEntity> findByTipAndUser(TipEntity tipEntity, UserEntity userEntity);

    @Transactional
    @Modifying
    @Query("update TipInfoEntity r set r.isHeart = :isHeart where r.tipInfoId = :id")
    void updateisHeart(@Param("isHeart") boolean isHeart, @Param("id")Long id);
}
