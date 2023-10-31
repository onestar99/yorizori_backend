package com.kkkj.yorijori_be.Service.Recipe;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkkj.yorijori_be.Dto.Recipe.*;
import com.kkkj.yorijori_be.Dto.User.UserCommentDto;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeDetailEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeIngredientTagEntity;
import com.kkkj.yorijori_be.Entity.SpecialDayFoodEntity;
import com.kkkj.yorijori_be.Entity.User.UserCommentEntity;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeCategoryTagRepository;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeDetailRepository;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeIngredientTagRepository;
import com.kkkj.yorijori_be.Repository.Recipe.RecipeRepository;
import com.kkkj.yorijori_be.Repository.SpecialDayFoodRepository;
import com.kkkj.yorijori_be.Repository.User.UserCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeGetService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientTagRepository recipeIngredientTagRepository;
    private final RecipeRecommendService recipeRecommendService;
    private final UserCommentRepository userCommentRepository;
    private final SpecialDayFoodRepository specialDayFoodRepository;

    // 레시피 정보 페이징해서 보내기.
    public Page<RecipeListDto> getRecipePaging(int pageNo, int pageSize, String sortBy){

        // json 형식을 Entity에 맞춰서 칼럼 명칭 변환
        String columnName = sortToColumnName(sortBy);

        // 페이지 인스턴스 생성
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(columnName).descending());
        Page<RecipeEntity> recipeEntityPage = recipeRepository.findAll(pageable);
        Page<RecipeListDto> recipeListDtoPage = RecipeListDto.toDtoPage(recipeEntityPage);
        return recipeListDtoPage;
    }
    // 레시피 정보 페이징해서 보내기.
    public Page<RecipeListDto> getRecipeYorizoriRankPaging(int pageNo, int pageSize){
        // 페이지 인스턴스 생성
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<RecipeEntity> recipeEntityPage = recipeRepository.findTopRecipesPage(pageable);
        Page<RecipeListDto> recipeListDtoPage = RecipeListDto.toDtoPage(recipeEntityPage);
        return recipeListDtoPage;
    }




    // 레시피 카테고리 전체 정보 페이징해서 보내기.
    public Page<RecipeListDto> getRecipeCategoryAllPaging(int pageNo, int pageSize, String orderBy){

        // json 형식을 Entity에 맞춰서 칼럼 명칭 변환
//        String columnName = sortToColumnName(sortBy);

        // 페이지 인스턴스 생성
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(orderBy).descending());
        Page<RecipeEntity> recipeEntityPage = recipeRepository.findAll(pageable);
        Page<RecipeListDto> recipeListDtoPage = RecipeListDto.toDtoPage(recipeEntityPage);
        return recipeListDtoPage;
    }

    public Page<RecipeListDto> getRecipeCategoryPaging(int pageNo, int pageSize, String categoryName, String orderBy){

        // 요리조리 정렬
        if(orderBy.equals("yorizori")){
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<RecipeEntity> recipeEntityPage = recipeRepository.findRecipesWithCategoryAndWeight(pageable, categoryName);
            Page<RecipeListDto> recipeListDtoPage = RecipeListDto.toDtoPage(recipeEntityPage);
            return recipeListDtoPage;
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(orderBy).descending());
        // 카테고리 이름 이용해서 레시피 아이디들을 받기
        List<Long> recipeIdList = getRecipeIdsByCategory(categoryName);
        Page<RecipeEntity> recipeEntityPage = recipeRepository.findAllByRecipeIdIn(recipeIdList, pageable);
        Page<RecipeListDto> recipeListDtoPage = RecipeListDto.toDtoPage(recipeEntityPage);
        return recipeListDtoPage;
    }

    // 유저 아이디를 통해 레시피 목록 페이징 제공
    public Page<RecipeListDto> getRecipePagingByUserId(int pageNo, int pageSize, String userId){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdTime").descending());
        Page<RecipeEntity> recipeEntityPage = recipeRepository.findAllByUser_UserTokenId(userId, pageable);
        Page<RecipeListDto> recipeListDtoPage = RecipeListDto.toDtoPage(recipeEntityPage);
        return recipeListDtoPage;

    }



    // "일식" 카테고리에 해당하는 recipe_id들을 가져오는 로직
    private List<Long> getRecipeIdsByCategory(String category) {
        // ... 로직을 구현해야 함
        // 예를 들어, 'recipe_category' 테이블에서 "일식" 카테고리에 해당하는 recipe_id들을 조회하는 쿼리를 실행하고 결과를 반환
        List<Long> recipeEntityList = recipeRepository.findRecipeIdByCategory(category);
        return recipeEntityList;

    }


    // 레시피 디테일 정보 보내주기
    public RecipeDetailsDto getRecipeDetailsByRecipeId(Long recipeId){

        // recipeEntity 만들기
        RecipeEntity recipeEntity = recipeRepository.findByRecipeId(recipeId);

        // List<RecipeIngredientDto> mainIngredient 만들기
        List<RecipeIngredientTagEntity> recipeIngredientMainList = recipeIngredientTagRepository.findByRecipeAndIsMain(recipeEntity, "main");
        List<RecipeIngredientDto> recipeIngredientMainDtoList = new ArrayList<>();
        for(RecipeIngredientTagEntity recipeIngredientTagEntity: recipeIngredientMainList){
            recipeIngredientMainDtoList.add(RecipeIngredientDto.toDto(recipeIngredientTagEntity));
        }

        // List<RecipeIngredientDto> semiIngredient 만들기
        List<RecipeIngredientTagEntity> recipeIngredientSemiList = recipeIngredientTagRepository.findByRecipeAndIsMain(recipeEntity, "semi");
        List<RecipeIngredientDto> recipeIngredientSemiDtoList = new ArrayList<>();
        for(RecipeIngredientTagEntity recipeIngredientTagEntity: recipeIngredientSemiList){
            recipeIngredientSemiDtoList.add(RecipeIngredientDto.toDto(recipeIngredientTagEntity));
        }

        // List<RecipeOrderDto> order 만들기
        List<RecipeDetailEntity> recipeDetailEntityList = recipeEntity.getDetails();
        List<RecipeOrderDto> recipeOrderDtoList = new ArrayList<>();
        for(RecipeDetailEntity recipeDetailEntity: recipeDetailEntityList){
            recipeOrderDtoList.add(RecipeOrderDto.toDto(recipeDetailEntity));
        }


        // 레시피 원작자 리스트 만들기
        List<RecipeReferenceRecipeDto> referenceRecipeDtoList = new ArrayList<>();
        String referenceStr = recipeEntity.getReferenceRecipe();
        System.out.println(referenceStr);
        // 원작자 값이 null 아닌 경우
        if (referenceStr != null){
            // ','을 기준으로 tokenizing 해서 recipeIds 만들기
            String[] parts = referenceStr.split(",");
            List<Long> recipeIds = new ArrayList<>();
            for (String part : parts) {
                recipeIds.add(Long.parseLong(part));
            }

            // 역순으로 sorting
            Collections.reverse(recipeIds);

            for(long longRecipeId: recipeIds){
                RecipeEntity recipe = recipeRepository.findByRecipeId(longRecipeId);

                if (recipe == null){
//                    referenceRecipeDtoList.add(null);
                    RecipeReferenceRecipeDto referenceRecipeDto = RecipeReferenceRecipeDto.builder()
                            .recipeId(-1)
                            .recipeTitle(null)
                            .nickname(null)
                            .profileImage(null)
                            .build();
                    referenceRecipeDtoList.add(referenceRecipeDto);
                }
                else{
                    RecipeReferenceRecipeDto referenceRecipeDto = RecipeReferenceRecipeDto.builder()
                            .recipeId(longRecipeId)
                            .recipeTitle(recipe.getRecipeTitle())
                            .nickname(recipe.getUser().getNickname())
                            .profileImage(recipe.getUser().getImageAddress())
                            .build();
                    referenceRecipeDtoList.add(referenceRecipeDto);
                }

            }
            System.out.println(referenceRecipeDtoList);
        }



        // 4개 합쳐서 DTO 완성 후 return
        RecipeDetailsDto recipeDetailsDto = RecipeDetailsDto.toDto(recipeEntity, recipeIngredientMainDtoList,
                recipeIngredientSemiDtoList, recipeOrderDtoList, referenceRecipeDtoList);

        return recipeDetailsDto;

    }


    // 조회수순으로 랭크 9위까지 정렬
    public List<RecipeListDto> getTop9ItemsByViews() {

        List<RecipeEntity> recipeEntityList = recipeRepository.findTop9ByOrderByRecipeViewCountDesc();
        List<RecipeListDto> recipeListDtoList = new ArrayList<>();
        for(RecipeEntity recipeEntity: recipeEntityList){
            recipeListDtoList.add(RecipeListDto.toDto(recipeEntity));
        }
        return recipeListDtoList;
    }

    // 조회수순으로 랭크 100위까지 정렬
    public List<RecipeListDto> getTop100ItemsByViews() {

        List<RecipeEntity> recipeEntityList = recipeRepository.findTop100ByOrderByRecipeViewCountDesc();
        List<RecipeListDto> recipeListDtoList = new ArrayList<>();
        for(RecipeEntity recipeEntity: recipeEntityList){
            recipeListDtoList.add(RecipeListDto.toDto(recipeEntity));
        }
        return recipeListDtoList;
    }

//    // 조회수순으로 랭크 100위까지 정렬
//    public Page<RecipeListDto> getTopPagingItemsByViews(int pageNo, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("recipeHits").descending());
//        Page<RecipeEntity> recipeEntityPage = recipeRepository.findAll(pageable);
//        Page<RecipeListDto> recipeListDtoPage = RecipeListDto.toDtoPage(recipeEntityPage);
//
//        return recipeListDtoPage;
//    }




    /*
    * getRecipePaging() 함수에서 현재 사용중인 함수
    * Json 에서 보내오는 변수명과 Java Entity에서의 변수명이 달라 호환을 위해 만든 convert 함수.
    * */
    private String sortToColumnName(String sortBy){
        String columnName = null;
        switch (sortBy){
            case "id":
                columnName = "recipeId";
                break;
            case "viewCount":
                columnName = "recipeViewCount";
                break;
            case "starRate":
                columnName = "scope";
                break;
        }
        return columnName;
    }

    public Page<RecipeListDto> recipeSearchList(String searchKeyword,int pageNo,String orderBy){

        int pageSize = 12;

        // 요리조리 정렬로 요청이 오면 요리조리 정렬로 보내줌
        if(orderBy.equals("yorizori")){
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<RecipeEntity> recipeEntityList = recipeRepository.findRecipesWithWeightAndKeyword(pageable, searchKeyword);
            Page<RecipeListDto> recipeListDtoPage = RecipeListDto.toDtoPage(recipeEntityList);
            return recipeListDtoPage;
        }

        // 일반적인 정렬의 경우 orderBy에 따라서 정렬로 보내줌
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(orderBy).descending());
        Page<RecipeEntity> recipeEntityList = recipeRepository.findByRecipeTitleContaining(searchKeyword,pageable);
        Page<RecipeListDto> recipeListDtoPage = RecipeListDto.toDtoPage(recipeEntityList);


        return recipeListDtoPage;
    }



    // 동적으로 재료 포함한 레시피 검색
    public Page<RecipeListDto> recipeIngredientAllSearchList(List<String> ingredients,int pageNo,String orderBy){
        List<RecipeListDto> recipeListDtoList = new ArrayList<>();//레시피를 받는 리스트
        for(int i=0;i<ingredients.size();i++) {
            List<RecipeEntity> recipeEntityList = recipeRepository.searchingredient(ingredients.get(i).strip());//재료를 가지고 있는 레시피 리스트
            if(i==0){
                for (RecipeEntity recipeEntity: recipeEntityList) {
                    recipeListDtoList.add(RecipeListDto.toDto(recipeEntity));
                }
            }
            else{
                List<RecipeListDto> temprecipeListDtoList = new ArrayList<>();//재료를 가진 임시 레시피 리스트
                for (RecipeEntity recipeEntity : recipeEntityList) {
                    temprecipeListDtoList.add(RecipeListDto.toDto(recipeEntity));
                }
                List<RecipeListDto> finalRecipeListDtoList = recipeListDtoList;//이전 조건문의 재료를 가진 레시피 리스트
                List<RecipeListDto> matchList = temprecipeListDtoList.stream().filter(o -> finalRecipeListDtoList.stream().anyMatch(n->{return o.getId().equals(n.getId());})).collect(Collectors.toList());// 재료를 가진 레시피 리스트를 비교하여 중복되는 레시피 추출
                recipeListDtoList=matchList;
            }
        }
        int pageSize=12;
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize,Sort.by(orderBy).descending());
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start+pageRequest.getPageSize()),recipeListDtoList.size());
        Page<RecipeListDto> recipeListDtoPage = new PageImpl<>(recipeListDtoList.subList(start,end),pageRequest,recipeListDtoList.size());

        return recipeListDtoPage;
    }


    // 레시피 디테일을 보낼 때 리뷰를 보내줌.
    public RecipeDetailReviewDto getRecipeDetailReview(Long boardId) {

        RecipeEntity recipe = recipeRepository.findByRecipeId(boardId);

        List<Integer> starCount = new ArrayList<>();
        List<ReviewDto> reviewDtoList = new ArrayList<>();

        // starCount 만들기
        starCount.add(userCommentRepository.countCommentsWithStarCount5ForBoard(boardId));
        starCount.add(userCommentRepository.countCommentsWithStarCount4ForBoard(boardId));
        starCount.add(userCommentRepository.countCommentsWithStarCount3ForBoard(boardId));
        starCount.add(userCommentRepository.countCommentsWithStarCount2ForBoard(boardId));
        starCount.add(userCommentRepository.countCommentsWithStarCount1ForBoard(boardId));


        // ReviewDto 만들기
        List<UserCommentEntity> userCommentEntityList = userCommentRepository.findByBoardOrderByCreatedTimeDesc(recipe);
        for(UserCommentEntity userCommentEntity: userCommentEntityList){
            reviewDtoList.add(ReviewDto.toDto(userCommentEntity));
        }

        String status = "ok";
        if(userCommentEntityList.isEmpty()){
            status = "null";
        }

        return RecipeDetailReviewDto.builder()
                .status(status)
                .recipeStarCount(recipe.getStarCount())
                .userStarCount(starCount)
                .reviewCount(recipe.getReviewCount())
                .reviews(reviewDtoList)
                .build();

    }


    public List<RecipeListDto> todayRecommend(int getSize) throws IOException {

//        LocalDate now = LocalDate.of(2023, 5, 5);
        LocalDate now = LocalDate.now();
        List<SpecialDayFoodEntity> specialDayFoodEntityList = specialDayFoodRepository.findBySpecialDay(now);

        // 특별한 날이 맞다면
        if(!specialDayFoodEntityList.isEmpty()) {
            return getRecipesBySpecialDay(specialDayFoodEntityList, getSize);
        }

        /*
        * ① 0 : 없음
        ② 1 : 비
        ③ 2 : 비/눈
        ④ 3 : 눈/비
        ⑤ 4 : 눈
        * */
//        int weatherNum = 1;
        int weatherNum = getWeatherApi();
        if(weatherNum == 1){
            // 비 오면 부침개 보여주기
            return getRecipesForRain(getSize);
        }

        // 나머지 상황은 플라스크에서 날짜를 고려한 추천
        return recipeRecommendService.todayRecommendByRecipeId();



    }


    public List<RecipeListDto> testTodayRecommend(int getSize, String month, String day, String rain) throws IOException {


        /*
        * ① 0 : 없음
        ② 1 : 비
        ③ 2 : 비/눈
        ④ 3 : 눈/비
        ⑤ 4 : 눈
        * */
        if(Objects.equals(rain, "on")){
            int weatherNum = 1;
    //        int weatherNum = getWeatherApi();
            if(weatherNum == 1){
                // 비 오면 부침개 보여주기
                return getRecipesForRain(getSize);
            }
        }

        LocalDate now = LocalDate.of(2023, Integer.parseInt(month), Integer.parseInt(day));
//        LocalDate now = LocalDate.now();
        List<SpecialDayFoodEntity> specialDayFoodEntityList = specialDayFoodRepository.findBySpecialDay(now);

        // 특별한 날이 맞다면
        if(!specialDayFoodEntityList.isEmpty()) {
            return getRecipesBySpecialDay(specialDayFoodEntityList, getSize);
        }

        // 나머지 상황은 플라스크에서 날짜를 고려한 추천
        return recipeRecommendService.todayRecommendByRecipeId();



    }



//    // 오늘의 추천 (오늘 날짜 월,일 기준 작년의 5일 후 데이터와 올해 5일 전까지의 데이터 추합)
//    public List<RecipeListDto> getRecipesDateRecommend(int getSize) {
//        LocalDate now = LocalDate.now();
////        LocalDate now = LocalDate.of(2023, 1, 1);
//        LocalDate fiveDaysAgoThisYear = now.minusDays(5);
//        LocalDate fiveDaysAfterLastYearSameDate = now.minusYears(1).plusDays(5);
//
//        // 1월 1일 기준 12월 27일 데이터부터 1월 1일까지
//        List<RecipeEntity> recipesThisYear = recipeRepository.findRecipesBetweenDates(fiveDaysAgoThisYear.toString(), now.plusDays(1).toString());
//        // 1월 1일 기준 1월 1일 데이터부터 1월 6일까지
//        List<RecipeEntity> recipesLastYear = recipeRepository.findRecipesBetweenDates(now.minusYears(1).toString(), fiveDaysAfterLastYearSameDate.toString());
//
//        // 두 날짜의 레시피 데이터 합치고 섞기
//        recipesThisYear.addAll(recipesLastYear);
//        List<RecipeEntity> getRecipes = getRandomRecipes(recipesThisYear, getSize);
//
//        // toDto 작업
//        List<RecipeListDto> recipeListDtoList = new ArrayList<>();
//        for(RecipeEntity recipeEntity: getRecipes){
//            recipeListDtoList.add(RecipeListDto.toDto(recipeEntity));
//        }
//        return recipeListDtoList;
//    }

    // 비오는 날 음식 불러오기
    public List<RecipeListDto> getRecipesForRain(int getSize) {
        List<String> foodList = new ArrayList<>();
        String str = "파전, 막걸리, 두부김치, 부침개, 국밥";
        // 쉼표(,)를 기준으로 문자열을 자르고 배열에 저장
        String[] items = str.split(", ");
        // 배열의 내용을 리스트에 추가
        foodList.addAll(Arrays.asList(items));

        List<RecipeEntity> matchingRecipes = new ArrayList<>();
        // 음식들 조회
        for (String ingredient : foodList) {
            List<RecipeEntity> recipesWithIngredient = recipeRepository.findByRecipeTitleContaining(ingredient);
            matchingRecipes.addAll(recipesWithIngredient);
        }
        // 12개만큼 얻어내기
        List<RecipeEntity> randomRecipes = getRandomRecipes(matchingRecipes, getSize);

        // toDto 작업
        List<RecipeListDto> recipeListDtoList = new ArrayList<>();
        for(RecipeEntity recipeEntity: randomRecipes){
            recipeListDtoList.add(RecipeListDto.toDto(recipeEntity));
        }

        return recipeListDtoList;

    }


    // 특별한 날 레시피 얻기
    public List<RecipeListDto> getRecipesBySpecialDay(List<SpecialDayFoodEntity> specialDayFoodEntityList, int getSize){

        List<String> foodList = new ArrayList<>();
        // 해당날짜 특별한날이 있다면 List<String> 으로 food 모두 모으기
        if(!specialDayFoodEntityList.isEmpty()){
            for(SpecialDayFoodEntity specialDayFood: specialDayFoodEntityList){
                String str = specialDayFood.getDayFood();
                // 쉼표(,)를 기준으로 문자열을 자르고 배열에 저장
                String[] items = str.split(", ");
                // 배열의 내용을 리스트에 추가
                foodList.addAll(Arrays.asList(items));
            }
        } else{
            return null;
        }
        List<RecipeEntity> matchingRecipes = new ArrayList<>();
        // 음식들 조회
        for (String ingredient : foodList) {
            List<RecipeEntity> recipesWithIngredient = recipeRepository.findByRecipeTitleContaining(ingredient);
            matchingRecipes.addAll(recipesWithIngredient);
        }
        // 12개만큼 얻어내기
        List<RecipeEntity> randomRecipes = getRandomRecipes(matchingRecipes, getSize);

        // toDto 작업
        List<RecipeListDto> recipeListDtoList = new ArrayList<>();
        for(RecipeEntity recipeEntity: randomRecipes){
            recipeListDtoList.add(RecipeListDto.toDto(recipeEntity));
        }

        return recipeListDtoList;

    }


    // 요리조리 정렬 추천 시스템 (생성날짜, 별점, 리뷰수, 조회수 고려)
    public List<RecipeListDto> getRecipesByRecommendSystem(int getSize) {
        List<RecipeEntity> recipesRecommends = recipeRepository.findTopRecipes(getSize);
        List<RecipeListDto> recipeListDtoList = new ArrayList<>();
        for(RecipeEntity recipeEntity: recipesRecommends){
            recipeListDtoList.add(RecipeListDto.toDto(recipeEntity));
        }
        return recipeListDtoList;
    }




    // 레시피 desiredSize 만큼 랜덤하게 줄이기
    private List<RecipeEntity> getRandomRecipes(List<RecipeEntity> matchingRecipes, int desiredSize) {
        // 원하는 크기보다 작거나 같으면 그대로 반환
        if (matchingRecipes.size() <= desiredSize) {
            return matchingRecipes;
        }

        List<RecipeEntity> randomRecipes = new ArrayList<>(matchingRecipes);

        // 랜덤으로 원소 제거
        Random random = new Random();
        while (randomRecipes.size() > desiredSize) {
            int indexToRemove = random.nextInt(randomRecipes.size());
            randomRecipes.remove(indexToRemove);
        }
        Collections.shuffle(randomRecipes);

        return randomRecipes;
    }

    private int getWeatherApi() throws IOException {
        // 비가 온다면?
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=hN94ej1IHGPuB86nRzChmRRNN%2BKonkmQf19bt2lsqG%2FD0BAPDyAV2kvdmtKNfAjaIwYgV%2Bfxudisa0N75YjCOQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/

        // 현재 날짜 가져오기
        LocalDate nowDay = LocalDate.now();
        // 날짜를 "yyyyMMdd" 형식의 문자열로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = nowDay.format(formatter);

        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(formattedDate, "UTF-8")); /*‘21년 6월 28일 발표*/

        // 현재 날짜와 시각을 가져오기
        LocalDateTime nowHour = LocalDateTime.now();
        // 1시간 전 구하기
        LocalDateTime oneHourAgo = nowHour.minus(1, ChronoUnit.HOURS);
        // "hhmm" 형식으로 포맷팅하기
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HHmm");
        String formattedTime = oneHourAgo.format(formatter2);
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(formattedTime, "UTF-8")); /*06시 발표(정시단위) */
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("58", "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("125", "UTF-8")); /*예보지점의 Y 좌표값*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        // JSON 문자열을 파싱할 ObjectMapper 생성
        ObjectMapper objectMapper = new ObjectMapper();
        // JSON 문자열 파싱
        JsonNode jsonNode = objectMapper.readTree(sb.toString());
        // items에 접근
        JsonNode itemsNode = jsonNode.at("/response/body/items/item");
        // 첫 번째 item에서 obsrValue 얻기
        String obsrValue = itemsNode.get(0).get("obsrValue").asText();

        return Integer.parseInt(obsrValue);
    }
}
