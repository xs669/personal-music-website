package com.xs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xs.common.Result;
import com.xs.domain.Singer;

/**
 * @author xs
 * description 针对表【singer(歌手)】的数据库操作Service
 * createDate 2022-10-05 15:08:13
*/
public interface SingerService extends IService<Singer> {

    /**
     * 添加歌手
     */
    Result addSinger(Singer singer);

    /**
     * 修改歌手
     */
    Result updateSinger(Singer singer);

    /**
     * 删除歌手
     */
    Result deleteSinger(Long id);

    /**
     * 分页查询所有歌手
     */
    Result getAllSingerByPage(Integer currentPage);

    /**
     * 按名字模糊查询歌手
     */
    Result singerOfName(String name);

    /**
     * 批量删除歌手
     */
    Result deleteAllSinger(Long[] ids);

    /**
     * 获取歌手总数
     */
    Result getSingerCount();

    /**
     * 按歌手id查询歌手名
     */
    Result getSingerNameById(Long id);

    /**
     * 按地区分组查询歌手数量
     */
    Result getSingerCountByLocation();

    /**
     * 按性别分组查询歌手数量
     */
    Result getSingerCountBySex();

    /**
     * 按性别查询所有歌手
     */
    Result getAllSingerBySex(Integer sex);

    /**
     * 按id查询歌手
     */
    Result getSingerById(Long id);

    /**
     * 查询所有歌手
     */
    Result getAllSinger();

    /**
     * 分页查询所有歌手
     */
    Result getSingerByPage(Integer currentPage);

    /**
     * 按性别分页查询所有歌手
     */
    Result getAllSingerBySexAndPage(Integer sex, Integer currentPage);

    /**
     * 按性别查询歌手数量
     */
    Result getAllSingerCountBySex(Integer sex);
}
