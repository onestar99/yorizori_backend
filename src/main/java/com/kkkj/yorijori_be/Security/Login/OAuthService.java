package com.kkkj.yorijori_be.Security.Login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
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
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    private final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
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

    private final UserRepository userRepository;
    private final UserSaveUpdateService userSaveUpdateService;



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
                    .user(userDto)
                    .build();
        }else{
            System.out.println("이미 토큰아이디가 존재합니다.");
            UserDto userDto = UserDto.toUserDto(user);

            return LoginDto.builder()
                    .status("login Successful")
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

        // 요청 구글 URI
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
        String image = jsonNode.path("kakao_account").path("profile").path("thumbnail_image_url").asText();
        String age = jsonNode.path("kakao_account").path("age_range").asText();
        String gender = jsonNode.path("kakao_account").path("gender").asText();

        UserEntity user = userRepository.findByUserTokenId(id);
        if(user == null){ // 정보가 없어서 회원가입
            System.out.println("회원가입을 시도합니다.");
            UserDto userDto = UserDto.builder()
                    .userTokenId(id)
                    .gender(gender)
                    .age(age)
                    .imageAddress(image)
                    .oauthDivision("kakao")
                    .nickname(nickName)
                    .build();
            userSaveUpdateService.saveUser(userDto);

            return LoginDto.builder()
                    .status("signUp Successful")
                    .user(userDto)
                    .build();
        }else{
            System.out.println("이미 토큰아이디가 존재합니다.");
            UserDto userDto = UserDto.toUserDto(user);

            return LoginDto.builder()
                    .status("login Successful")
                    .user(userDto)
                    .build();
        }
    }




    private String jsonParsing(ResponseEntity<String> response, String type) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.getBody());

        return root.get(type).asText();
    }


}
