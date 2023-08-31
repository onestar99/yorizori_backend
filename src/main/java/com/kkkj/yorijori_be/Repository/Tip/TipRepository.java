package com.kkkj.yorijori_be.Repository.Tip;

import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipRepository extends JpaRepository<TipEntity, String> {

    TipEntity findByTipId(Long tipId);

    Page<TipEntity> findAllByUser_UserTokenId(String user_userTokenId, Pageable pageable);
}
