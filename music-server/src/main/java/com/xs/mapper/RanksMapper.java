package com.xs.mapper;

import com.xs.domain.Ranks;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xs
 * description 针对表【Rank(评价)】的数据库操作Mapper
 * createDate 2022-10-11 16:16:20
 * Entity com.xs.domain.Rank
*/
@Repository
@Mapper
public interface RanksMapper extends BaseMapper<Ranks> {

    /**
     * 按歌单id删除评价信息
     */
    int deleteBySongListId(Long id);

    /**
     * 按用户id删除评价信息
     */
    int deleteByConsumerId(Long id);

    /**
     * 获取所有歌单评价
     */
    List<Ranks> getAllRanks();

    /**
     * 获取指定歌单评价总分
     */
    double[] getAllScore(Long id);

    /**
     * 获取指定歌单评价数量
     */
    int getCountBySongListId(Long id);
}




