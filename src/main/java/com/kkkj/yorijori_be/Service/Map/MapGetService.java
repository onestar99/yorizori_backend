package com.kkkj.yorijori_be.Service.Map;

import com.kkkj.yorijori_be.Dto.Map.MapLocationDto;
import com.kkkj.yorijori_be.Dto.Map.MapSearchDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;


@Service
public class MapGetService {


    /*
     * 검색 Query를 넘겨주면 Map 검색에서 정보를 가져옴.
     *
     * */
    public List<MapSearchDto> getSearchResult(String location, String foodName){
        String clientId = "v8qkCPFTa9NgzFpMFY_u"; //애플리케이션 클라이언트 아이디
        String clientSecret = "rQlNSJSVSo"; //애플리케이션 클라이언트 시크릿

        String searchQuery = location + " " + foodName + " 맛집";

        String text = null;
        try {
            text = URLEncoder.encode(searchQuery, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/local.json?query=" + text + "&display=5";    // JSON 결과

//        comment: 업체 및 기관에 대한 카페, 블로그의 리뷰 개수순으로 내림차순 정렬
//        String apiURL2 = "https://openapi.naver.com/v1/search/local.json?query=" + text + "&display=5" + "&sort=comment";    // JSON 결과

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);


        List<MapSearchDto> mapSearchDtoList = parsingJsonBySearch(location, responseBody);

        return mapSearchDtoList;

    }


    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }

    private List<MapSearchDto> parsingJsonBySearch(String location, String responseBody){

        // JSON 파싱
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray items = jsonObject.getJSONArray("items");

        List<MapSearchDto> mapSearchDtoList = new ArrayList<>();

        // "title" 값을 추출하여 출력
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            String title = item.getString("title");

            // HTML 태그를 제거하고 문자만 남기는 문자열
            String stripTitle = title.replaceAll("<[^>]*>", " ");
            stripTitle = stripTitle.trim();
            // "amp;"을 제거한 문자열
            stripTitle = stripTitle.replace("amp;", "");
            stripTitle = stripTitle.replace("  ", " ");

            String address = item.getString("address");
            String category = item.getString("category");
            String roadAddress = item.getString("roadAddress");
            String mapx = item.getString("mapx");
            String mapy = item.getString("mapy");

            String mapxStr = mapx.substring(0, 3) + "." + mapx.substring(3); // 127.~
            String mapyStr = mapy.substring(0, 2) + "." + mapy.substring(2); // 37.~


            // 카테고리 split 작업 ex) '한식>베이커리' => '베이커리'
            String[] parts = category.split(">");
            String splitCategory = category;
            if (parts.length > 1) {
                splitCategory = parts[parts.length - 1]; // 마지막 ">" 이후의 부분을 선택
            }

            // 문자열을 공백으로 분할
            String[] parts2 = location.split(" ");
            // 마지막 공백 뒤에 나오는 글자 추출
            String locationLastWord = parts2[parts2.length - 1];


            // 맵 링크 검색 쿼리
            String mapQuery = locationLastWord + " " + stripTitle;

            String encodedString = "";
            try {
                // 문자열을 URL 인코딩
                encodedString = URLEncoder.encode(mapQuery, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String link = "https://map.naver.com/p/search/" + encodedString +"?c=15.00,0,0,0,dh";


            MapSearchDto mapSearchDto = new MapSearchDto(stripTitle, address, splitCategory, roadAddress, mapxStr, mapyStr, link);
            mapSearchDtoList.add(mapSearchDto);
        }

        return mapSearchDtoList;
    }





    public String getLocation(MapLocationDto mapLocationDto){

        String clientId = "r2gbdh2tvp"; //애플리케이션 클라이언트 아이디
        String clientSecret = "Q1ap2hWKonfzE89hqlfaeVxE3m5gkgbU9fjfoYOB"; //애플리케이션 클라이언트 시크릿

        String apiURL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?request=coordsToaddr&coords="
                + mapLocationDto.getLongitude() + "," + mapLocationDto.getLatitude() + "&sourcecrs=epsg:4326&output=json&orders=legalcode";    // JSON 결과

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-NCP-APIGW-API-KEY-ID", clientId);
        requestHeaders.put("X-NCP-APIGW-API-KEY", clientSecret);
        String responseBody = get(apiURL,requestHeaders);

//        System.out.println(responseBody);
        String combinedAreaNames = combineAreaNames(responseBody);
        System.out.println(combinedAreaNames);


        return combinedAreaNames;


    }

    private static String combineAreaNames(String jsonData) {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray results = jsonObject.getJSONArray("results");

        StringBuilder combinedNames = new StringBuilder();

        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            JSONObject region = result.getJSONObject("region");

//            String area0 = region.getJSONObject("area0").getString("name");
            String area1 = region.getJSONObject("area1").getString("name");
            String area2 = region.getJSONObject("area2").getString("name");
            String area3 = region.getJSONObject("area3").getString("name");
            String area4 = region.getJSONObject("area4").getString("name");


            // 이름들을 합친 문자열을 만듭니다.
//            String combinedName = area1 + " " + area2 + " " + area3 + " " + area4;
            String combinedName;
            boolean area1IsSpecialCity = area1.contains("특별시");
            if (area1IsSpecialCity){ // 첫번째로 오는것이 특별시라면?
                combinedName = area1 + " " + area2;
            }else{ // 첫번째로 오는것이 도
                combinedName = area1 + " " + area2 + " " + area3;
            }

            // 공백으로 구분하여 합친 이름들을 StringBuilder에 추가합니다.
            combinedNames.append(combinedName).append(" ");
        }

        return combinedNames.toString().trim(); // 앞뒤 공백 제거
    }
}
