package com.xs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.Result;
import com.xs.domain.Ranks;
import com.xs.mapper.RanksMapper;
import com.xs.service.RankService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

/**
 * @author xs
 * description 针对表【Ranks(评价)】的数据库操作Service实现
 * createDate 2022-10-11 16:16:20
*/
@Service
public class RankServiceImpl extends ServiceImpl<RanksMapper, Ranks> implements RankService {

    @Resource
    private RanksMapper ranksMapper;

    /**
     * 添加歌单评价
     */
    @Override
    public Result addRanks(Ranks ranks) {
        List<Ranks> ranksList = ranksMapper.getAllRanks();
        if (!ranksList.isEmpty()) {
            for (Ranks r : ranksList) {
                if (r.getSongListId().equals(ranks.getSongListId()) && r.getConsumerId().equals(ranks.getConsumerId())) {
                    return Result.error("用户不能重复评价");
                }
            }
        }
        int i = ranksMapper.insert(ranks);
        if (i > 0) {
            return Result.ok("评价成功");
        } else {
            return Result.error("评价失败");
        }
    }

    /**
     * 获取指定歌单的平均分
     */
    @Override
    public Result getRanksAvgScore(Long id) {
        double[] allScore = ranksMapper.getAllScore(id);
        double count = 0;
        if (Objects.nonNull(allScore) && allScore.length != 0) {
            for (double i : allScore) {
                count += i;
            }
            int number = ranksMapper.getCountBySongListId(id);
            if (number == 0) {
                return Result.error("数据为空");
            } else {
                double avgScore = count / number / 2;
                return Result.ok("查询成功", new DecimalFormat("0.0").format(avgScore));
            }
        } else {
            return Result.error("数据为空");
        }
    }
}




