package com.xs.controller;

import com.xs.common.Result;
import com.xs.domain.SongList;
import com.xs.service.SongListService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/songList")
@RestController
public class SongListController {

    @Resource
    private SongListService songListService;

    /**
     * 分页查询所有歌单
     */
    @GetMapping("/all/{currentPage}")
    public Result getSongListByPage(@PathVariable Integer currentPage) {
        return songListService.getSongListByPage(currentPage);
    }

    /**
     * 查询所有歌单
     */
    @GetMapping("/all")
    public Result getAllSongList() {
        return songListService.getAllSongList();
    }

    /**
     * 按标题查询歌单
     */
    @GetMapping("/songListOfName/{name}")
    public Result getSongListByName(@PathVariable String name) {
        return songListService.getSongListByName(name);
    }

    /**
     * 添加歌单
     */
    @PostMapping("/add")
    public Result addSongList(@RequestBody SongList songList) {
        return songListService.addSongList(songList);
    }

    /**
     * 修改歌单
     */
    @PutMapping("/update")
    public Result updateSongList(@RequestBody SongList songList) {
        return songListService.updateSongList(songList);
    }

    /**
     * 删除歌单
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteSongList(@PathVariable Long id) {
        return songListService.deleteSongList(id);
    }

    /**
     * 批量删除歌单
     */
    @DeleteMapping("/deleteAll/{ids}")
    public Result deleteAllSongList(@PathVariable Long[] ids) {
        return songListService.deleteAllSongList(ids);
    }

    /**
     * 获取歌单总数
     */
    @GetMapping("/getCount")
    public Result getSongListCount() {
        return songListService.getSongListCount();
    }

    /**
     * 按风格查询歌单数量
     */
    @GetMapping("/songListCountByStyle/{style}")
    public Result getAllSongListCountByStyle(@PathVariable String style) {
        return songListService.getAllSongListCountByStyle(style);
    }

    /**
     * 按风格分组查询歌单数量
     */
    @GetMapping("/getSongListCountByStyle")
    public Result getSongListCountByStyle() {
        return songListService.getSongListCountByStyle();
    }

    /**
     * 按歌单风格名称查询所有歌单
     */
    @GetMapping("/getSongListByStyle/{style}")
    public Result getSongListByStyle(@PathVariable String style) {
        return songListService.getSongListByStyle(style);
    }

    /**
     * 按歌单风格名称分组查询所有歌单
     */
    @GetMapping("/getSongListByStyle/{style}/{currentPage}")
    public Result getSongListByStyleAndPage(@PathVariable String style, @PathVariable Integer currentPage) {
        return songListService.getSongListByStyleAndPage(style, currentPage);
    }

    /**
     * 查询所有歌单的风格并统计
     */
    @GetMapping("/allStyle")
    public Result getAllStyle() {
        return songListService.getAllStyle();
    }
}
