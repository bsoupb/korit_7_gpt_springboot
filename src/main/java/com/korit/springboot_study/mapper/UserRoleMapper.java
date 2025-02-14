package com.korit.springboot_study.mapper;

import com.korit.springboot_study.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRoleMapper {
    int insert(UserRole userRole);
    int insertSelective(@Param("userId") int userId, @Param("roleName") String roleName);
}
