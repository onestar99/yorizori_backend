package com.kkkj.yorijori_be.Service.Recipe;

import com.kkkj.yorijori_be.Dto.Recipe.RecipeListDto;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeRecommendService {

    private final RecipeRepository recipeRepository;


    /*
    레시피 추천 서비스
    return 레시피 리스트
     */
    public List<RecipeListDto> recipeRecommendByRecipeId(String userId){

        // 플라스크에서 레시피 추천 아이디 받기
        String userIds = getFlaskRecommendRecipeIds(userId);
        // Str To List
        List<Long> userIdList = convertStringToList(userIds);
        // 레시피 정보 조회
        List<RecipeEntity> recipeEntityList = recipeRepository.findByRecipeIdIn(userIdList);
        // 레시피 정보 변환
        List<RecipeListDto> recipeListDtoList = new ArrayList<>();
        for(RecipeEntity recipeEntity: recipeEntityList){
            recipeListDtoList.add(RecipeListDto.toDto(recipeEntity));
        }
        return recipeListDtoList;

    }


    /*
    * 파이썬하고 통신해서 추천 레시피 받아오기.
    * */
    private String getFlaskRecommendRecipeIds(String userId){

        // Flask 서버의 URL로 GET 요청
        String flaskUrl = "http://localhost:5000/recommend/" + userId; // Flask 서버의 주소
        // RestTemplate 생성
        RestTemplate restTemplate = new RestTemplate();
        // Flask로 GET 요청 보내기
        ResponseEntity<String> response = restTemplate.getForEntity(flaskUrl, String.class);
        // 응답을 받아서 처리
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            return responseBody;
        } else {
            return "Failed to get data from Flask";
        }

    }


    /*
    * Str to List
     */
    private List<Long> convertStringToList(String input) {

        List<Long> resultList = new ArrayList<>();
        // 정규 표현식을 사용하여 문자열에서 숫자를 추출
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            // 매칭된 숫자를 Long 형으로 변환하여 리스트에 추가
            Long number = Long.parseLong(matcher.group());
            resultList.add(number);
        }

        return resultList;
    }
}
