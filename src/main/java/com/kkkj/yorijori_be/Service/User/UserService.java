package com.kkkj.yorijori_be.Service.User;

import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;


    @Transactional
    public void saveUser(UserDto userDto){
        userRepository.save(userDto.toEntity());
    }
}
