package com.kkkj.yorijori_be.Repository.Tip;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TipRepository extends JpaRepository<TipEntity, String> {

    TipEntity findByTipId(Long tipId);

    Page<TipEntity> findAllByUser_UserTokenId(String user_userTokenId, Pageable pageable);

    //검색
    Page<TipEntity> findByTipTitleContaining(String searchKeywod, Pageable pageable);

    //tip의 타이틀, 디테일, 썸네일을 query문으로 업데이트
    @Modifying
    @Query("update TipEntity t set t.tipTitle = :tipTitle, t.tipDetail = :tipDetail, t.tipThumbnail = :tipThumbnail where t.tipId = :id")
    void updateAll(@Param("id")Long id,@Param("tipTitle")String tipTitle,@Param("tipDetail")String tipDetail,@Param("tipThumbnail")String tipThumbnail);

    @Modifying
    @Query("update TipEntity t set t.tipViewCount = t.tipViewCount + 1 where t.tipId = :id")
    void updateViewCount(@Param("id")Long id);

    @Modifying
    @Query("update TipEntity t set t.tipReviewCount = t.tipReviewCount + 1 where t.tipId = :id")
    void updateReviewCount(@Param("id")Long id);

    @Transactional
    @Modifying
    @Query("update TipEntity t set t.tipHeartCount = t.tipHeartCount + :var where t.tipId = :id")
    void updateHeartCount(@Param("id")Long id,@Param("var")int var);
}
