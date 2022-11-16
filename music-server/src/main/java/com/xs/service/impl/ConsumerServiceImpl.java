package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.Result;
import com.xs.domain.Consumer;
import com.xs.dto.ConsumerDto;
import com.xs.mapper.CollectMapper;
import com.xs.mapper.CommentMapper;
import com.xs.mapper.ConsumerMapper;
import com.xs.mapper.RanksMapper;
import com.xs.service.ConsumerService;
import com.xs.vo.LoginVo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author xs
 * description 针对表【consumer(前端用户)】的数据库操作Service实现
 * createDate 2022-10-11 12:58:48
*/
@Service
public class ConsumerServiceImpl extends ServiceImpl<ConsumerMapper, Consumer> implements ConsumerService{

    @Resource
    private ConsumerMapper consumerMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private RanksMapper ranksMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 查询所有用户
     */
    @Override
    public Result getAll() {
        List<Consumer> allConsumer = consumerMapper.getAllConsumer();
        if (Objects.nonNull(allConsumer) && !allConsumer.isEmpty()) {
            return Result.ok("查询成功", allConsumer);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 修改用户信息
     */
    @Override
    public Result updateConsumer(Consumer consumer) {
        Consumer consumer1 = consumerMapper.selectById(consumer.getId());
        if (!consumer.equals(consumer1)) {
            consumer.setPassword(passwordEncoder.encode(consumer.getPassword()));
            int i = consumerMapper.updateById(consumer);
            if (i > 0) {
                return Result.ok("更新成功");
            } else {
                return Result.error("更新失败");
            }
        } else {
            return Result.error("修改信息没有变化");
        }
    }

    /**
     * 按用户名称查询用户信息
     */
    @Override
    public Result getConsumerByName(String name) {
        LambdaQueryWrapper<Consumer> lqw = new LambdaQueryWrapper<>();
        lqw.like(Objects.nonNull(name), Consumer::getUsername, name);
        List<Consumer> consumers = consumerMapper.selectList(lqw);
        if (!consumers.isEmpty()) {
            return Result.ok("查询成功", consumers);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 添加用户
     */
    @Override
    public Result addConsumer(Consumer consumer) {
        List<Consumer> allConsumer = consumerMapper.getAllConsumer();
        if (!allConsumer.isEmpty()) {
            for (Consumer cs : allConsumer) {
                if (cs.getUsername().equals(consumer.getUsername())) {
                    return Result.error("用户名不能重复");
                }
            }
        }
        consumer.setPassword(passwordEncoder.encode(consumer.getPassword()));
        int insert = consumerMapper.insert(consumer);
        if (insert > 0) {
            return Result.ok("添加成功");
        } else {
            return Result.error("添加失败");
        }
    }

    /**
     * 删除用户
     */
    @Override
    public Result deleteConsumer(Long id) {
        commentMapper.deleteByUserId(id);
        collectMapper.deleteByUserId(id);
        ranksMapper.deleteByConsumerId(id);
        int i = consumerMapper.deleteById(id);
        if (i > 0) {
            return Result.ok("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }

    /**
     * 批量删除用户
     */
    @Override
    public Result deleteAllConsumer(Long[] ids) {
        for (Long id : ids) {
            commentMapper.deleteByUserId(id);
            collectMapper.deleteByUserId(id);
            ranksMapper.deleteByConsumerId(id);
            consumerMapper.deleteById(id);
        }
        return Result.ok("删除成功");
    }

    /**
     * 获取用户总数
     */
    @Override
    public Result getConsumerCount() {
        int consumerCount = consumerMapper.getConsumerCount();
        return Result.ok("查询成功", consumerCount);
    }

    /**
     * 按性别分组查询用户数量
     */
    @Override
    public Result getConsumerCountBySex() {
        List<ConsumerDto> consumerCountBySex = consumerMapper.getConsumerCountBySex();
        if (!consumerCountBySex.isEmpty()) {
            return Result.ok("查询成功", consumerCountBySex);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 用户登录
     */
    @Override
    public Result login(LoginVo loginVo) {
        LambdaQueryWrapper<Consumer> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Consumer::getUsername, loginVo.getUsername());
        Consumer consumer = consumerMapper.selectOne(lqw);
        if (Objects.nonNull(consumer)) {
            if (passwordEncoder.matches(loginVo.getPassword(), consumer.getPassword())) {
                return Result.ok("登录成功", consumer);
            } else {
                return Result.error("密码错误");
            }
        } else {
            return Result.error("用户不存在");
        }
    }

    /**
     * 按id查询用户信息
     */
    @Override
    public Result getConsumerById(Long id) {
        Consumer consumer = consumerMapper.selectById(id);
        if (Objects.nonNull(consumer)) {
            return Result.ok("查询成功", consumer);
        } else {
            return Result.error("查询失败");
        }
    }

}




