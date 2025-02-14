package com.korit.springboot_study.mapper;

import com.korit.springboot_study.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int insert(User user);
    User selectByUsername(String name);
    User selectById(int userId);
    List<User> select();
    int updateUserById(User user);
    int deleteById(int userId);
}
