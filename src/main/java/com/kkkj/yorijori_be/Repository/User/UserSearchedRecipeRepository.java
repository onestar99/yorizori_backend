package com.kkkj.yorijori_be.Repository.User;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Entity.User.UserSearchedRecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSearchedRecipeRepository extends JpaRepository<UserSearchedRecipeEntity, String> {



}
