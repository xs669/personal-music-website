package com.xs.controller.admin;

import com.xs.common.Result;
import com.xs.domain.Singer;
import com.xs.service.SingerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/singer")
public class SingerAdminController {

    @Resource
    private SingerService singerService;

    /**
     * 添加歌手
     */
    @PostMapping("/add")
    public Result addSinger(@RequestBody Singer singer) {
        return singerService.addSinger(singer);
    }

    /**
     * 修改歌手
     */
    @PutMapping("/update")
    public Result updateSinger(@RequestBody Singer singer) {
        return singerService.updateSinger(singer);
    }

    /**
     * 删除歌手
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteSinger(@PathVariable Long id) {
        return singerService.deleteSinger(id);
    }

    /**
     * 批量删除歌手
     */
    @DeleteMapping("/deleteAll/{ids}")
    public Result deleteAllSinger(@PathVariable Long[] ids) {
        return singerService.deleteAllSinger(ids);
    }

    /**
     * 分页查询所有歌手
     */
    @GetMapping("/allSinger/{currentPage}")
    public Result getAllSingerByPage(@PathVariable Integer currentPage) {
        return singerService.getAllSingerByPage(currentPage);
    }

    /**
     * 查询所有歌手
     */
    @GetMapping("/allSinger")
    public Result getAllSinger() {
        return singerService.getAllSinger();
    }

    /**
     * 按名字模糊查询歌手
     */
    @GetMapping("/singerOfName/{name}")
    public Result singerOfName(@PathVariable String name) {
        return singerService.singerOfName(name);
    }

    /**
     * 获取歌手总数
     */
    @GetMapping("/getCount")
    public Result getSingerCount() {
        return singerService.getSingerCount();
    }

    /**
     * 按歌手id查询歌手名
     */
    @GetMapping("/getSingerNameById/{id}")
    public Result getSingerNameById(@PathVariable Long id) {
        return singerService.getSingerNameById(id);
    }

    /**
     * 按地区分组查询歌手数量
     */
    @GetMapping("/getCountByLocation")
    public Result getSingerCountByLocation() {
        return singerService.getSingerCountByLocation();
    }

    /**
     * 按性别分组查询歌手数量
     */
    @GetMapping("/getCountBySex")
    public Result getSingerCountBySex() {
        return singerService.getSingerCountBySex();
    }

    /**
     * 按id查询歌手
     */
    @GetMapping("/getSingerById/{id}")
    public Result getSingerById(@PathVariable Long id) {
        return singerService.getSingerById(id);
    }

    /**
     * 按性别查询所有歌手
     */
    @GetMapping("/getAllSingerBySex/{sex}")
    public Result getAllSingerBySex(@PathVariable Integer sex) {
        return singerService.getAllSingerBySex(sex);
    }
}
