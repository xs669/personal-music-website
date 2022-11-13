package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.domain.Admin;
import com.xs.mapper.AdminMapper;
import com.xs.service.AdminService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author xs
 * description 针对表【admin(管理员)】的数据库操作Service实现
 * createDate 2022-10-03 16:22:14
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<Admin> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Admin::getUsername, username);
        Admin admin = adminMapper.selectOne(lqw);
        if (Objects.isNull(admin)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return admin;
    }
}




