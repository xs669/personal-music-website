package com.xs.controller;

import com.xs.common.Result;
import com.xs.domain.Ranks;
import com.xs.service.RankService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/rank")
public class RankController {

    @Resource
    private RankService rankService;

    /**
     * 添加歌单评价
     */
    @PostMapping("/add")
    public Result addRank(@RequestBody Ranks ranks) {
        return rankService.addRanks(ranks);
    }

    /**
     * 获取指定歌单的平均分
     */
    @GetMapping("/getAvgScore/{id}")
    public Result getRanksAvgScore(@PathVariable Long id) {
        return rankService.getRanksAvgScore(id);
    }
}
