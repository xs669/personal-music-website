package com.xs.service;

import com.xs.common.Result;
import com.xs.domain.Ranks;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author xs
 * description 针对表【rank(评价)】的数据库操作Service
 * createDate 2022-10-11 16:16:20
*/
public interface RankService extends IService<Ranks> {

    /**
     * 添加歌单评价
     */
    Result addRanks(Ranks ranks);

    /**
     * 获取指定歌单的平均分
     */
    Result getRanksAvgScore(Long id);
}
