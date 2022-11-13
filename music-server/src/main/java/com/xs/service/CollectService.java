package com.xs.service;

import com.xs.common.Result;
import com.xs.domain.Collect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xs.dto.SearchDto;
import com.xs.vo.CollectVo;

/**
 * @author xs
 * description 针对表【collect(收藏)】的数据库操作Service
 * createDate 2022-10-11 16:14:36
*/
public interface CollectService extends IService<Collect> {

    /**
     * 收藏歌曲
     */
    Result addCollect(Collect collect);

    /**
     * 取消收藏
     */
    Result cancelCollect(CollectVo collectVo);

    /**
     * 查询歌曲收藏状态
     */
    Result getCollectBySongId(CollectVo collectVo);

    /**
     * 获取指定用户所有收藏歌曲
     */
    Result getAllCollectByConsumerId(Long id);

    /**
     * 查询歌单收藏状态
     */
    Result getCollectBySongListId(CollectVo collectVo);

    /**
     * 获取指定用户所有收藏歌单
     */
    Result getAllCollectSongListByUserId(Long id);

    /**
     * 获取指定用户收藏信息
     */
    Result getAllCollectByUserId(Long id);

    /**
     * 删除指定用户收藏数据
     */
    Result delCollect(Long id);

    /**
     * 批量删除指定用户收藏数据
     */
    Result delAllCollect(Long[] ids);

    /**
     * 模糊搜索指定用户收藏信息
     */
    Result searchCollectByUserId(SearchDto searchDto);
}
