package com.kkkj.yorijori_be.Repository.User;

import com.kkkj.yorijori_be.Entity.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,String> {


}
