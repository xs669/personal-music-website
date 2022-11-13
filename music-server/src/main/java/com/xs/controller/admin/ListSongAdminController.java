package com.xs.controller.admin;

import com.xs.common.Result;
import com.xs.dto.ListSongByNameDto;
import com.xs.service.ListSongService;
import com.xs.vo.ListSongVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/admin/listSong")
@RestController
public class ListSongAdminController {

    @Resource
    private ListSongService listSongService;

    /**
     * 查找歌手对应的歌曲名称
     */
    @GetMapping("/songOfName/{singId}")
    public Result getSongNameBySingerId(@PathVariable Long singId) {
        return listSongService.getSongNameBySingerId(singId);
    }

    /**
     * 查询所有歌单歌曲
     */
    @GetMapping("/all/{id}")
    public Result getAll(@PathVariable Long id) {
        return listSongService.getAll(id);
    }

    /**
     * 添加歌单歌曲
     */
    @PostMapping("/add")
    public Result addListSong(@RequestBody ListSongVo listSongVo) {
        return listSongService.addListSong(listSongVo);
    }

    /**
     * 删除歌单歌曲
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteListSong(@PathVariable Long id) {
        return listSongService.deleteListSong(id);
    }

    /**
     * 批量删除歌曲
     */
    @DeleteMapping("/deleteAll/{ids}")
    public Result deleteAllListSong(@PathVariable Long[] ids) {
        return listSongService.deleteAllListSong(ids);
    }

    /**
     * 按名称查询歌单歌曲
     */
    @PostMapping("/listSongOfName")
    public Result getListSongByName(@RequestBody ListSongByNameDto listSongByNameDto) {
        return listSongService.getListSongByName(listSongByNameDto);
    }
}
