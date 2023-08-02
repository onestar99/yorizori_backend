package com.kkkj.yorijori_be.Security.Status;

public class ResponseMessage {
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";

    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
    public static final String CREATED_USER = "회원 가입 성공";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String DELETE_USER = "회원 탈퇴 성공";

    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터베이스 에러";

    public static final String UPLOAD_EXTENSION_ERROR = "업로드 확장자 오류";
    public static final String UPLOAD_FAIL = "업로드 실패";
    public static final String UPLOAD_SUCCESS = "업로드 성공";

    public static final String RECIPE_FOUND_SUCCESS = "레시피 정보 조회 성공";
    public static final String RECIPE_FOUND_FAIL = "레시피 정보 조회 실패";
    public static final String RECIPE_CATEGORY_FOUND_FAIL = "레시피 카테고리 정보 조회 실패, 카테고리를 정확하게 썼는지 확인해주세요.";
}