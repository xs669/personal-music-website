package com.xs.controller.admin;

import com.xs.common.Result;
import com.xs.domain.Collect;
import com.xs.dto.SearchDto;
import com.xs.service.CollectService;
import com.xs.vo.CollectVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/collect")
public class CollectAdminController {

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

    /**
     * 获取指定用户收藏信息
     */
    @GetMapping("/collectOfUserId/{id}")
    public Result getAllCollectByUserId(@PathVariable Long id) {
        return collectService.getAllCollectByUserId(id);
    }

    /**
     * 删除指定用户收藏数据
     */
    @DeleteMapping("/del/{id}")
    public Result delCollect(@PathVariable Long id) {
        return collectService.delCollect(id);
    }

    /**
     * 批量删除指定用户收藏数据
     */
    @DeleteMapping("/delAll/{ids}")
    public Result delAllCollect(@PathVariable Long[] ids) {
        return collectService.delAllCollect(ids);
    }

    /**
     * 模糊搜索指定用户收藏信息
     */
    @PostMapping("/searchOfUserId")
    public Result searchCollectByUserId(@RequestBody SearchDto searchDto) {
        return collectService.searchCollectByUserId(searchDto);
    }
}
