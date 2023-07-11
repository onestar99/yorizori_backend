package com.kkkj.yorijori_be.Service.User;

import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;



    // 유저 저장
    @Transactional
    public void saveUser(UserDto userDto){
        userRepository.save(userDto.toEntity());
    }

    // 모든 유저 검색
    public List<UserDto> findAllUser(){

        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        for (UserEntity userEntity: userEntityList){
            userDtoList.add(UserDto.toUserDto(userEntity));
        }

        return userDtoList;
    }


    // 유저 정보 페이징해서 보내기.
    public Page<UserEntity> getUserPaging(int pageNo, int pageSize, String sortBy){

        // 페이지 인스턴스 생성
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return userRepository.findAll(pageable);

    }
//
//    // 유저 정보 페이징해서 보내기.
//    public List<UserDto> getUserPaging(int pageNo, int pageSize, String sortBy){
//
//        // 페이지 인스턴스 생성
////        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
//        Pageable pageable = PageRequest.of(pageNo, pageSize);
//        Page<UserEntity> userPage = userRepository.findAll(pageable);
//
//        List<UserDto> userDtoList = new ArrayList<>();
//
//        for (UserEntity userEntity: userPage){
//            userDtoList.add(UserDto.toUserDto(userEntity));
//        }
//        return userDtoList;
//
//    }




    // 개인 유저 검색
    public UserDto findUserByTokenId(String userTokenId){

        Optional<UserEntity> optionalUserEntity = userRepository.findById(userTokenId);
        if(optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            return UserDto.toUserDto(userEntity);
        } else {
            return null;
        }
    }







}
