package com.xs.controller;

import com.xs.common.Result;
import com.xs.domain.Collect;
import com.xs.service.CollectService;
import com.xs.vo.CollectVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/collect")
public class CollectController {

    @Resource
    private CollectService collectService;

    /**
     * 收藏歌曲
     */
    @PostMapping("/add")
    public Result addCollect(@RequestBody Collect collect) {
        return collectService.addCollect(collect);
    }

    /**
     * 取消收藏
     */
    @DeleteMapping("/cancel")
    public Result cancelCollect(@RequestBody CollectVo collectVo) {
        return collectService.cancelCollect(collectVo);
    }

    /**
     * 查询歌曲收藏状态
     */
    @PostMapping("/getCollectBySongId")
    public Result getCollectBySongId(@RequestBody CollectVo collectVo) {
        return collectService.getCollectBySongId(collectVo);
    }

    /**
     * 查询歌单收藏状态
     */
    @PostMapping("/getCollectBySongListId")
    public Result getCollectBySongListId(@RequestBody CollectVo collectVo) {
        return collectService.getCollectBySongListId(collectVo);
    }

    /**
     * 获取指定用户所有收藏歌曲
     */
    @GetMapping("/collectOfConsumerId/{id}")
    public Result getAllCollectByConsumerId(@PathVariable Long id) {
        return collectService.getAllCollectByConsumerId(id);
    }

    /**
     * 获取指定用户所有收藏歌单
     */
    @GetMapping("/collectSongListOfUserId/{id}")
    public Result getAllCollectSongListByUserId(@PathVariable Long id) {
        return collectService.getAllCollectSongListByUserId(id);
    }
}
