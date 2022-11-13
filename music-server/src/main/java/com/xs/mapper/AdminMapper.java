package com.xs.mapper;

import com.xs.domain.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author xs
 * description 针对表【admin(管理员)】的数据库操作Mapper
 * createDate 2022-10-03 16:22:14
 * Entity com.xs.domain.Admin
*/
@Repository
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    Admin findByUsername(String username);
}




