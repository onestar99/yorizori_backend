package com.kkkj.yorijori_be.Repository.User;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import com.kkkj.yorijori_be.Entity.User.UserCommentEntity;
import com.kkkj.yorijori_be.Entity.User.UserTipCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserTipCommentRepository extends JpaRepository<UserTipCommentEntity, Long> {

    List<UserTipCommentEntity> findByBoard(TipEntity board);


    /*
    * 게시글 댓글 별점별 개수
    * */
    @Query("SELECT COUNT(c) FROM UserTipCommentEntity c WHERE c.board.tipId = :boardId AND c.scope = '5.0'")
    Long countTipCommentsWithScope5ForBoard(@Param("boardId")Long boardId);

    @Query("SELECT COUNT(c) FROM UserTipCommentEntity c WHERE c.board.tipId = :boardId AND c.scope = '4.0'")
    Long countTipCommentsWithScope4ForBoard(@Param("boardId")Long boardId);

    @Query("SELECT COUNT(c) FROM UserTipCommentEntity c WHERE c.board.tipId = :boardId AND c.scope = '3.0'")
    Long countTipCommentsWithScope3ForBoard(@Param("boardId")Long boardId);

    @Query("SELECT COUNT(c) FROM UserTipCommentEntity c WHERE c.board.tipId = :boardId AND c.scope = '2.0'")
    Long countTipCommentsWithScope2ForBoard(@Param("boardId")Long boardId);

    @Query("SELECT COUNT(c) FROM UserTipCommentEntity c WHERE c.board.tipId = :boardId AND c.scope = '1.0'")
    Long countTipCommentsWithScope1ForBoard(@Param("boardId")Long boardId);


}
