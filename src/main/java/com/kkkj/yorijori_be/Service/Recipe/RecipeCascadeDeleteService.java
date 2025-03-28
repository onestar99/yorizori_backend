package com.kkkj.yorijori_be.Service.Recipe;

import com.kkkj.yorijori_be.Service.Log.LogDeleteService;
import com.kkkj.yorijori_be.Service.User.UserDeleteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeCascadeDeleteService {

    private final RecipeDeleteService recipeDeleteService;
    private final LogDeleteService logDeleteService;
    private final UserDeleteService userDeleteService;

    /**
     * 하나의 트랜잭션 내에서 레시피와 관련된 모든 데이터를 삭제
     * 트랜젝션 적용, 실패시 전체 록백, 일관성 유지
     */

    @Transactional
    public boolean deleteRecipeCompletely(Long recipeId) {

        // 레시피 뷰 로그 삭제
        boolean logsDeleted = logDeleteService.deleteUserViewLogsByRecipeId(recipeId);
        // 레시피 댓글 삭제
        boolean commentsDeleted = userDeleteService.deleteAllCommentByRecipeId(recipeId);
        // 레시피와 연관된 모든 데이터 삭제 (썸네일, 디테일, 재료, 카테고리, 템플릿 등)
        boolean recipeDeleted = recipeDeleteService.deleteRecipeCascadeByRecipeId(recipeId);

        return logsDeleted && commentsDeleted & recipeDeleted;

    }
}
