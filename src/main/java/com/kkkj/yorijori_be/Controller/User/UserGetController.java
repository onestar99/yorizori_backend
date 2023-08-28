package com.kkkj.yorijori_be.Controller.User;

import com.kkkj.yorijori_be.Dto.Recipe.RecipeListDto;
import com.kkkj.yorijori_be.Dto.Tip.TipListDto;
import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import com.kkkj.yorijori_be.Entity.User.*;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import com.kkkj.yorijori_be.Service.Recipe.RecipeGetService;
import com.kkkj.yorijori_be.Service.Tip.TipGetService;
import com.kkkj.yorijori_be.Service.User.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/get")
public class UserGetController {


    private final UserGetService userGetService;
    private final UserRepository userRepository;
    private final RecipeGetService recipeGetService;
    private final TipGetService tipGetService;

    // 모든 유저 정보 조회
    // return -> json
    @GetMapping("/all") @ResponseBody
    public List<UserDto> getAllUser(){
        List<UserDto> userDtoList = userGetService.findAllUser();
        return userDtoList;
    }


    // 모든 유저 정보 페이징 처리
    @GetMapping("/all/paging") @ResponseBody
    public Page<UserEntity> getAllPaging(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "user_token_id", required = false) String sortBy
            ){
        return userGetService.getUserPaging(pageNo, pageSize, sortBy);
    }


    // 유저 tokenId를 통한 조회
    // return -> json
    @GetMapping("/{userTokenId}") @ResponseBody
    public UserDto getByUserTokenId(@PathVariable("userTokenId") String userTokenId ) {
        return userGetService.findUserByTokenId(userTokenId);
    }



    @GetMapping("/{userTokenId}/comments") @ResponseBody
    public ResponseEntity<List<UserCommentEntity>> getUserComments(@PathVariable String userTokenId) {
        UserEntity user = userRepository.findByUserTokenId(userTokenId);
        List<UserCommentEntity> comments = user.getComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{userTokenId}/tipcomments") @ResponseBody
    public ResponseEntity<List<UserTipCommentEntity>> getUserTipComments(@PathVariable String userTokenId) {
        UserEntity user = userRepository.findByUserTokenId(userTokenId);
        List<UserTipCommentEntity> tipcomments = user.getTipComments();
        return ResponseEntity.ok(tipcomments);
    }

    @GetMapping("/{userTokenId}/viewlog") @ResponseBody
    public ResponseEntity<List<UserViewLogEntity>> getUserViewLog(@PathVariable String userTokenId) {
        UserEntity user = userRepository.findByUserTokenId(userTokenId);
        List<UserViewLogEntity> viewLog = user.getViewLog();
        return ResponseEntity.ok(viewLog);
    }


    @GetMapping("/{userTokenId}/tips") @ResponseBody
    public Page<TipListDto> getTipsPaging(@PathVariable String userTokenId,@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo){
        // 페이지 사이즈 고정
        int pageSize = 12;
        Page<TipListDto> tipListDtoPage = tipGetService.getTipsPagingByUserId(pageNo,pageSize,userTokenId);
        return tipListDtoPage;
    }

    @GetMapping("/{userTokenId}/searchedrecipelog") @ResponseBody
    public ResponseEntity<List<UserSearchedRecipeEntity>> getUserSearchedRecipeLog(@PathVariable String userTokenId) {
        UserEntity user = userRepository.findByUserTokenId(userTokenId);
        List<UserSearchedRecipeEntity> searchedRecipeEntityList = user.getSearchedRecipe();
        return ResponseEntity.ok(searchedRecipeEntityList);
    }

    @GetMapping("/{userTokenId}/searchedingredientlog") @ResponseBody
    public ResponseEntity<List<UserSearchedIngredientEntity>> getUserSearchedIngredientLog(@PathVariable String userTokenId) {
        UserEntity user = userRepository.findByUserTokenId(userTokenId);
        List<UserSearchedIngredientEntity> searchedIngredientEntityList = user.getSearchedIngredient();
        return ResponseEntity.ok(searchedIngredientEntityList);
    }

    // 유저 토큰 아이디를 받고 유저의 게시글 목록을 보여주는 API
    @ResponseBody
    @GetMapping("/{userTokenId}/recipe")
    public Page<RecipeListDto> getRecipeByUserId(@PathVariable String userTokenId,
                                                 @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo){
        // 페이지 사이즈 고정
        int pageSize = 12;
        // 카테고리 이름이 all 이 아니라면 카테고리에 맞춰서 조회
        Page<RecipeListDto> recipeListDtoPage = recipeGetService.getRecipePagingByUserId(pageNo, pageSize, userTokenId);
        return recipeListDtoPage;
    }

}
