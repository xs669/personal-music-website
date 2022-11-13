package com.xs.controller.admin;

import com.xs.common.Result;
import com.xs.domain.Consumer;
import com.xs.service.ConsumerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/admin/consumer")
@RestController
public class ConsumerAdminController {

    @Resource
    private ConsumerService consumerService;

    /**
     * 查询所有用户
     */
    @GetMapping("/all")
    public Result getAll() {
        return consumerService.getAll();
    }

    /**
     * 修改用户信息
     */
    @PutMapping("/update")
    public Result updateConsumer(@RequestBody Consumer consumer) {
        return consumerService.updateConsumer(consumer);
    }

    /**
     * 按用户名称查询用户信息
     */
    @GetMapping("/consumerOfName/{name}")
    public Result getConsumerByName(@PathVariable String name) {
        return consumerService.getConsumerByName(name);
    }

    /**
     * 添加用户
     */
    @PostMapping("/add")
    public Result addConsumer(@RequestBody Consumer consumer) {
        return consumerService.addConsumer(consumer);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteConsumer(@PathVariable Long id) {
        return consumerService.deleteConsumer(id);
    }

    /**
     * 批量删除用户
     */
    @DeleteMapping("/deleteAll/{ids}")
    public Result deleteAllConsumer(@PathVariable Long[] ids) {
        return consumerService.deleteAllConsumer(ids);
    }

    /**
     * 获取用户总数
     */
    @GetMapping("/getCount")
    public Result getConsumerCount() {
        return consumerService.getConsumerCount();
    }

    /**
     * 按性别分组查询用户数量
     */
    @GetMapping("/getCountBySex")
    public Result getConsumerCountBySex() {
        return consumerService.getConsumerCountBySex();
    }

    /**
     * 按id查询用户信息
     */
    @GetMapping("/consumerOfId/{id}")
    public Result getConsumerById(@PathVariable Long id) {
        return consumerService.getConsumerById(id);
    }
}
