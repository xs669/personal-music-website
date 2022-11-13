package com.xs.service;

import com.xs.common.Result;
import com.xs.domain.Consumer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xs.vo.LoginVo;

import javax.servlet.http.HttpSession;

/**
 * @author xs
 * description 针对表【consumer(前端用户)】的数据库操作Service
 * createDate 2022-10-11 12:58:48
*/
public interface ConsumerService extends IService<Consumer> {

    /**
     * 查询所有用户
     */
    Result getAll();

    /**
     * 修改用户信息
     */
    Result updateConsumer(Consumer consumer);

    /**
     * 按用户名称查询用户信息
     */
    Result getConsumerByName(String name);

    /**
     * 添加用户
     */
    Result addConsumer(Consumer consumer);

    /**
     * 删除用户
     */
    Result deleteConsumer(Long id);

    /**
     * 批量删除用户
     */
    Result deleteAllConsumer(Long[] ids);

    /**
     * 获取用户总数
     */
    Result getConsumerCount();

    /**
     * 按性别分组查询用户数量
     */
    Result getConsumerCountBySex();

    /**
     * 用户登录
     */
    Result login(LoginVo loginVo);

    /**
     * 按id查询用户信息
     */
    Result getConsumerById(Long id);
}
