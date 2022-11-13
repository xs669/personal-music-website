package com.xs.controller;

import com.xs.common.Result;
import com.xs.domain.RecentSong;
import com.xs.service.RecentSongService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/recentSong")
@RestController
public class RecentSongController {

    @Resource
    private RecentSongService recentSongService;

    /**
     * 添加歌曲最近播放记录
     */
    @PostMapping("/add")
    public Result addRecentSong(@RequestBody RecentSong recentSong) {
        return recentSongService.addRecentSong(recentSong);
    }

    /**
     * 获取当前用户最近播放列表
     */
    @GetMapping("/recentSongOfUserId/{id}")
    public Result getRecentSongByUserId(@PathVariable Long id) {
        return recentSongService.getRecentSongByUserId(id);
    }

    /**
     * 推荐歌单
     */
    @GetMapping("/recommendSongList/{id}")
    public Result recommendSongList(@PathVariable Long id) {
        return recentSongService.recommendSongList(id);
    }

    /**
     * 推荐歌手
     */
    @GetMapping("/recommendSinger/{id}")
    public Result recommendSinger(@PathVariable Long id) {
        return recentSongService.recommendSinger(id);
    }
}
