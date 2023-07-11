package com.kkkj.yorijori_be.Service.User;

import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    // 유저 검색
    public void findAllUser(){

    }





}
