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
    @Query("SELECT COUNT(c) FROM UserCommentEntity c WHERE c.board.recipeId = :boardId AND c.starCount = 5")
    Integer countCommentsWithStarCount5ForBoard(@Param("boardId")Long boardId);

    @Query("SELECT COUNT(c) FROM UserCommentEntity c WHERE c.board.recipeId = :boardId AND c.starCount = 4")
    Integer countCommentsWithStarCount4ForBoard(@Param("boardId")Long boardId);

    @Query("SELECT COUNT(c) FROM UserCommentEntity c WHERE c.board.recipeId = :boardId AND c.starCount = 3")
    Integer countCommentsWithStarCount3ForBoard(@Param("boardId")Long boardId);

    @Query("SELECT COUNT(c) FROM UserCommentEntity c WHERE c.board.recipeId = :boardId AND c.starCount = 2")
    Integer countCommentsWithStarCount2ForBoard(@Param("boardId")Long boardId);

    @Query("SELECT COUNT(c) FROM UserCommentEntity c WHERE c.board.recipeId = :boardId AND c.starCount = 1")
    Integer countCommentsWithStarCount1ForBoard(@Param("boardId")Long boardId);

    @Query("SELECT AVG(c.starCount) FROM UserCommentEntity c WHERE c.board.recipeId = :boardId")
    Double averageRecipeStarCountByBoardId(@Param("boardId")Long boardId);


}
