package com.kkkj.yorijori_be.Repository.User;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByUserTokenId(String userTokenId);


}
