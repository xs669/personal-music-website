package com.xs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xs.domain.Admin;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author xs
 * description 针对表【admin(管理员)】的数据库操作Service
 * createDate 2022-10-03 16:22:14
*/
public interface AdminService extends IService<Admin>, UserDetailsService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
