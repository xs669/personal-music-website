package com.xs.controller;

import com.xs.common.Result;
import com.xs.domain.Song;
import com.xs.service.SongService;
import com.xs.vo.SongVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/song")
public class SongController {

    @Resource
    private SongService songService;

    /**
     * 添加歌曲
     */
    @PostMapping("/add")
    public Result addSong(@RequestBody Song song) {
        return songService.addSong(song);
    }

    /**
     * 按歌手id查询所有歌曲
     */
    @GetMapping("/allSong/{id}")
    public Result getAllSongBySingId(@PathVariable Long id) {
        return songService.getAllSongBySingId(id);
    }

    /**
     * 查询所有歌曲
     */
    @GetMapping("/all")
    public Result getAllSong() {
        return songService.getAllSong();
    }

    /**
     * 按歌单id查询所有歌曲
     */
    @GetMapping("/all/{id}")
    public Result getAllSongBySongListId(@PathVariable Long id) {
        return songService.getAllSongBySongListId(id);
    }

    /**
     * 更新歌曲
     */
    @PutMapping("/update")
    public Result updateSong(@RequestBody Song song) {
        return songService.updateSong(song);
    }

    /**
     * 按歌名查询歌曲
     */
    @GetMapping("/songOfName/{name}")
    public Result getAllSongByName(@PathVariable String name) {
        return songService.getAllSongByName(name);
    }

    /**
     * 按id删除歌曲
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteSong(@PathVariable Long id) {
        return songService.deleteSong(id);
    }

    /**
     * 批量删除歌曲
     */
    @DeleteMapping("/deleteAll/{ids}")
    public Result deleteAllSong(@PathVariable Long[] ids) {
        return songService.deleteAllSong(ids);
    }

    /**
     * 获取歌曲总数
     */
    @GetMapping("/getCount")
    public Result getSongCount() {
        return songService.getSongCount();
    }

    /**
     * 模糊查询某个歌手所属全部歌曲
     */
    @PostMapping("/songOfName")
    public Result getAllSongByNameAndSingerId(@RequestBody SongVo songVo) {
        return songService.getAllSongByNameAndSingerId(songVo);
    }

    /**
     * 按歌手id查询所有歌曲和歌手名
     */
    @GetMapping("/songAndSingerName/{id}")
    public Result gerAllSongAndSingerNameBySingerId(@PathVariable Long id) {
        return songService.gerAllSongAndSingerNameBySingerId(id);
    }

    /**
     * 增加播放量
     */
    @PostMapping("/addPlayCount")
    public Result addPlayCount(@RequestBody SongVo songVo) {
        return songService.addPlayCount(songVo);
    }

    /**
     * 按播放量排行
     */
    @GetMapping("/songOfPlayCount")
    public Result getSongByPlayCount() {
        return songService.getSongByPlayCount();
    }
}
