package com.kkkj.yorijori_be.Repository.Tip;

import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TipRepository extends JpaRepository<TipEntity, String> {

    TipEntity findByTipId(Long tipId);

    Page<TipEntity> findAllByUser_UserTokenId(String user_userTokenId, Pageable pageable);

    @Modifying
    @Query("update TipEntity t set t.tipViewCount = t.tipViewCount + 1 where t.tipId = :id")
    void updateViewCount(@Param("id")Long id);

}
