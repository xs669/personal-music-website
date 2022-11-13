package com.xs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xs.domain.Consumer;
import com.xs.dto.ConsumerDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xs
 * description 针对表【consumer(前端用户)】的数据库操作Mapper
 * createDate 2022-10-11 12:58:48
 * Entity com.xs.domain.Consumer
*/
@Repository
@Mapper
public interface ConsumerMapper extends BaseMapper<Consumer> {

    /**
     * 查询所有用户
     */
    List<Consumer> getAllConsumer();

    /**
     * 获取用户总数
     */
    int getConsumerCount();

    /**
     * 按性别统计用户数量
     */
    List<ConsumerDto> getConsumerCountBySex();

    /**
     * 用户登录
     */
    Consumer login(String username, String password);
}




