package com.kkkj.yorijori_be.Security.Login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkkj.yorijori_be.Cloud.S3Remover;
import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Entity.Recipe.RecipeEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import com.kkkj.yorijori_be.Service.User.UserDeleteService;
import com.kkkj.yorijori_be.Service.User.UserSaveUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    private final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private final String NAVER_TOKEN_URL = "https://nid.naver.com/oauth2.0/token";

    private final String KAKAO_LOGOUT_URL = "https://kapi.kakao.com/v1/user/logout";
    private final String KAKAO_UNLINK_URL = "https://kapi.kakao.com/v1/user/unlink";
    private String kakaoApiUrl = "https://kapi.kakao.com";

    @Value("${oauth2.google.client-id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${oauth2.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value("${oauth2.google.redirect-uri}")
    private String GOOGLE_REDIRECT_URI;

    @Value("${oauth2.kakao.client-id}")
    private String KAKAO_CLIENT_ID;
    @Value("${oauth2.kakao.redirect-uri}")
    private String KAKAO_REDIRECT_URI;

    @Value("${oauth2.naver.client-id}")
    private String NAVER_CLIENT_ID;
    @Value("${oauth2.naver.client-secret}")
    private String NAVER_CLIENT_SECRET;
    @Value("${oauth2.naver.redirect-uri}")
    private String NAVER_REDIRECT_URI;

    private final UserRepository userRepository;
    private final UserSaveUpdateService userSaveUpdateService;
    private final UserDeleteService userDeleteService;
    private final S3Remover s3Remover;



    // 구글 AccessToken 받기
    public ResponseEntity<String> getGoogleAccessToken(String accessCode) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();

        params.put("client_id", GOOGLE_CLIENT_ID);
        params.put("code", accessCode);
        params.put("client_secret", GOOGLE_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_REDIRECT_URI);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> response = restTemplate.postForEntity(GOOGLE_TOKEN_URL, params, String.class);
        if(response.getStatusCode() == HttpStatus.OK){
            return response;
        } else {
            return null;
        }
    }
    // 구글 유저 정보 JsonNode 형식으로 반환
    private JsonNode getGoogleUserInfo(String accessToken) throws IOException {

        // 요청 구글 URI
        String RequestUrl = "https://www.googleapis.com/oauth2/v2/userinfo";

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(RequestUrl);
        JsonNode returnNode = null;

        // add header
        get.addHeader("Authorization", "Bearer " + accessToken);

        client.execute(get);
        try {
            final HttpResponse responses = client.execute(get);
            final int responseCode = responses.getStatusLine().getStatusCode();

            ObjectMapper mapper = new ObjectMapper();
            returnNode = mapper.readTree(responses.getEntity().getContent());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {}
        return returnNode;
    }
    // 구글 로그인 시도, 회원가입 -> Controller
    public LoginDto googleLogin(String accessCode) throws IOException {
        ResponseEntity<String> response = getGoogleAccessToken(accessCode); // 구글로부터 토큰 response 얻어온다.
        String accessToken = jsonParsing(response, "access_token"); // access 토큰 파싱
        String idToken = jsonParsing(response, "id_token"); // id 토큰 파싱
        // TODO 엑세스 토큰을 이용하여 DB에서 기존 유저인지 찾아보고 (없으면 -> 회원가입 : 있으면 -> 로그인)
        JsonNode jsonNode = getGoogleUserInfo(accessToken); // 액세스 토큰으로 api 요청해서 유저정보 불러오기
        System.out.println("구글 액세스코드: " + accessToken);
        String id = jsonNode.get("id").asText();
        String nickName = jsonNode.get("name").asText();
        String image = jsonNode.get("picture").asText();

        UserEntity user = userRepository.findByUserTokenId(id);
        if(user == null){ // 정보가 없어서 회원가입
            System.out.println("회원가입을 시도합니다.");
            UserDto userDto = UserDto.builder()
                    .userTokenId(id)
                    .gender(null)
                    .age(null)
                    .imageAddress(image)
                    .oauthDivision("google")
                    .nickname(nickName)
                    .build();
            userSaveUpdateService.saveUser(userDto);

            return LoginDto.builder()
                    .status("signUp Successful")
                    .accessToken(accessToken)
                    .user(userDto)
                    .build();
        }else{
            System.out.println("이미 구글 토큰아이디가 존재합니다.");
            UserDto userDto = UserDto.toUserDto(user);

            return LoginDto.builder()
                    .status("login Successful")
                    .accessToken(accessToken)
                    .user(userDto)
                    .build();
        }
    }

    // 카카오 AccessToken 받기
    public ResponseEntity<String> getKakaoAccessToken(String accessCode) {

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", KAKAO_CLIENT_ID);
        params.add("redirect_uri", KAKAO_REDIRECT_URI);
        params.add("code", accessCode);

        ResponseEntity<String> response = restTemplate.postForEntity(KAKAO_TOKEN_URL, params, String.class);
        if(response.getStatusCode() == HttpStatus.OK){
            return response;
        } else {
            return null;
        }
    }
    // 카카오 유저 정보 JsonNode 형식으로 반환
    private JsonNode getKakaoUserInfo(String accessToken) throws IOException {

        // 요청 카카오 URI
        String RequestUrl = "https://kapi.kakao.com/v2/user/me";

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(RequestUrl);
        JsonNode returnNode = null;

        // add header
        get.addHeader("Authorization", "Bearer " + accessToken);

        client.execute(get);
        try {
            final HttpResponse responses = client.execute(get);
            final int responseCode = responses.getStatusLine().getStatusCode();

            ObjectMapper mapper = new ObjectMapper();
            returnNode = mapper.readTree(responses.getEntity().getContent());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {}
        return returnNode;
    }
    // 카카오 로그인 시도, 회원가입 -> Controller
    public LoginDto kakaoLogin(String accessCode) throws IOException {

        ResponseEntity<String> response = getKakaoAccessToken(accessCode); // 카카오로부터 토큰 response 얻어온다.
        String accessToken = jsonParsing(response, "access_token"); // access 토큰 파싱
        String idToken = jsonParsing(response, "id_token"); // id 토큰 파싱
        JsonNode jsonNode = getKakaoUserInfo(accessToken); // 액세스 토큰으로 api 요청해서 유저정보 불러오기

        String id = jsonNode.path("id").asText();
        String nickName = jsonNode.path("kakao_account").path("profile").path("nickname").asText();
        String image = jsonNode.path("kakao_account").path("profile").path("profile_image_url").asText();

        System.out.println("카카오 액세스코드: " + accessToken);
        UserEntity user = userRepository.findByUserTokenId(id);
        if(user == null){ // 정보가 없어서 회원가입
            System.out.println("회원가입을 시도합니다.");
            UserDto userDto = UserDto.builder()
                    .userTokenId(id)
                    .gender(null)
                    .age(null)
                    .imageAddress(image)
                    .oauthDivision("kakao")
                    .nickname(nickName)
                    .build();
            userSaveUpdateService.saveUser(userDto);

            return LoginDto.builder()
                    .status("signUp Successful")
                    .accessToken(accessToken)
                    .user(userDto)
                    .build();
        }else{
            System.out.println("이미 카카오 토큰아이디가 존재합니다.");
            UserDto userDto = UserDto.toUserDto(user);

            return LoginDto.builder()
                    .status("login Successful")
                    .accessToken(accessToken)
                    .user(userDto)
                    .build();
        }
    }


    // 네이버 AccessToken 받기
    public ResponseEntity<String> getNaverAccessToken(String accessCode) {

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", NAVER_CLIENT_ID);
        params.add("client_secret", NAVER_CLIENT_SECRET);
        params.add("code", accessCode);
        params.add("state", "test");

        ResponseEntity<String> response = restTemplate.postForEntity(NAVER_TOKEN_URL, params, String.class);
        if(response.getStatusCode() == HttpStatus.OK){
            return response;
        } else {
            return null;
        }
    }
    // 네이버 유저 정보 JsonNode 형식으로 반환
    private JsonNode getNaverUserInfo(String accessToken) throws IOException {

        // 요청 네이버 URI
        String RequestUrl = "https://openapi.naver.com/v1/nid/me";

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(RequestUrl);
        JsonNode returnNode = null;

        // add header
        get.addHeader("Authorization", "Bearer " + accessToken);

        client.execute(get);
        try {
            final HttpResponse responses = client.execute(get);
            final int responseCode = responses.getStatusLine().getStatusCode();

            ObjectMapper mapper = new ObjectMapper();
            returnNode = mapper.readTree(responses.getEntity().getContent());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {}
        return returnNode;
    }
    // 카카오 로그인 시도, 회원가입 -> Controller
    public LoginDto naverLogin(String accessCode) throws IOException {

        ResponseEntity<String> response = getNaverAccessToken(accessCode); // 카카오로부터 토큰 response 얻어온다.
        String accessToken = jsonParsing(response, "access_token"); // access 토큰 파싱
//        String idToken = jsonParsing(response, "id_token"); // id 토큰 파싱
        JsonNode jsonNode = getNaverUserInfo(accessToken); // 액세스 토큰으로 api 요청해서 유저정보 불러오기
        System.out.println("네이버 액세스코드: " + accessToken);
        String id = jsonNode.path("response").path("id").asText();
        String nickName = jsonNode.path("response").path("nickname").asText();
        String image = jsonNode.path("response").path("profile_image").asText();

        UserEntity user = userRepository.findByUserTokenId(id);
        if(user == null){ // 정보가 없어서 회원가입
            System.out.println("회원가입을 시도합니다.");
            UserDto userDto = UserDto.builder()
                    .userTokenId(id)
                    .gender(null)
                    .age(null)
                    .imageAddress(image)
                    .oauthDivision("naver")
                    .nickname(nickName)
                    .build();
            userSaveUpdateService.saveUser(userDto);

            return LoginDto.builder()
                    .status("signUp Successful")
                    .accessToken(accessToken)
                    .user(userDto)
                    .build();
        }else{
            System.out.println("이미 네이버 토큰아이디가 존재합니다.");
            UserDto userDto = UserDto.toUserDto(user);

            return LoginDto.builder()
                    .status("login Successful")
                    .accessToken(accessToken)
                    .user(userDto)
                    .build();
        }
    }




    private String jsonParsing(ResponseEntity<String> response, String type) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.getBody());
//        log.error(response.getBody());
//        log.error(root.path("access_token").asText());

        return root.path(type).asText();
    }


    // kakao 로그아웃 기능
    public ResponseEntity<String> kakaoLogout(String accessCode, long userTokenId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessCode);

        // 요청 본문 데이터 설정
        String requestBody = "target_id_type=user_id&target_id=" + Long.toString(userTokenId);// 대상 회원번호를 입력하세요

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.postForEntity(KAKAO_LOGOUT_URL, requestEntity, String.class);
        if(response.getStatusCode() == HttpStatus.OK){
            return response;
        } else {
            return null;
        }

    }

    // kakao 탈퇴 기능
    public ResponseEntity<String> kakaoUnlink(UserEntity user, String accessCode, String userTokenId) {

        // 유저 이미지 삭제
        try {
            s3Remover.deleteAbsoluteImage(user.getImageAddress());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        // 원작자 내용 찾아서 삭제
//        List<RecipeEntity> recipeEntityList = user.getRecipes();
//        List<Long> recipeIds = new ArrayList<>();
//        for(RecipeEntity recipe: recipeEntityList){
//            recipeIds.add(recipe.getRecipeId());
//        }

        // 유저 삭제하기
        userDeleteService.deleteUserById(userTokenId);
        System.out.println("유저삭제완료");


        // 유저 연결 끊기
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessCode);

        // 요청 본문 데이터 설정
        String requestBody = "target_id_type=user_id&target_id=" + userTokenId;// 대상 회원번호를 입력하세요

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.postForEntity(KAKAO_UNLINK_URL, requestEntity, String.class);
        if(response.getStatusCode() == HttpStatus.OK){
            return response;
        } else {
            return null;
        }

    }
}
