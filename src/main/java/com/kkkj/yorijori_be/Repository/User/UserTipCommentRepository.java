package com.kkkj.yorijori_be.Repository.User;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import com.kkkj.yorijori_be.Entity.User.UserCommentEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Entity.User.UserTipCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserTipCommentRepository extends JpaRepository<UserTipCommentEntity, Long> {

    List<UserTipCommentEntity> findByBoard(TipEntity tipEntity);

//    UserTipCommentEntity findByBoardAndUser(TipEntity tipEntity, UserEntity user);

}
