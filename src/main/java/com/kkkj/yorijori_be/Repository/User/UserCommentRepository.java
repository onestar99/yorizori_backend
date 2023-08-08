package com.kkkj.yorijori_be.Repository.User;

import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.User.UserCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCommentRepository extends JpaRepository<UserCommentEntity, Long> {

    List<UserCommentEntity> findByBoardOrderByCreatedTimeDesc(RecipeEntity board);


    /*
    * 게시글 댓글 별점별 개수
    * */
    @Query("SELECT COUNT(c) FROM UserCommentEntity c WHERE c.board.recipeId = :boardId AND c.scope = 5")
    Integer countCommentsWithScope5ForBoard(@Param("boardId")Long boardId);

    @Query("SELECT COUNT(c) FROM UserCommentEntity c WHERE c.board.recipeId = :boardId AND c.scope = 4")
    Integer countCommentsWithScope4ForBoard(@Param("boardId")Long boardId);

    @Query("SELECT COUNT(c) FROM UserCommentEntity c WHERE c.board.recipeId = :boardId AND c.scope = 3")
    Integer countCommentsWithScope3ForBoard(@Param("boardId")Long boardId);

    @Query("SELECT COUNT(c) FROM UserCommentEntity c WHERE c.board.recipeId = :boardId AND c.scope = 2")
    Integer countCommentsWithScope2ForBoard(@Param("boardId")Long boardId);

    @Query("SELECT COUNT(c) FROM UserCommentEntity c WHERE c.board.recipeId = :boardId AND c.scope = 1")
    Integer countCommentsWithScope1ForBoard(@Param("boardId")Long boardId);

    @Query("SELECT AVG(c.scope) FROM UserCommentEntity c WHERE c.board.recipeId = :boardId")
    Double averageRecipeScopeByBoardId(@Param("boardId")Long boardId);


}
