package com.xs.service;

import com.xs.common.Result;
import com.xs.domain.RecentSong;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author xs
 * description 针对表【recent_song】的数据库操作Service
 * createDate 2022-10-30 10:18:56
*/
public interface RecentSongService extends IService<RecentSong> {

    /**
     * 添加歌曲最近播放记录
     */
    Result addRecentSong(RecentSong recentSong);

    /**
     * 获取当前用户最近播放列表
     */
    Result getRecentSongByUserId(Long id);

    /**
     * 推荐歌单
     */
    Result recommendSongList(Long id);

    /**
     * 推荐歌手
     */
    Result recommendSinger(Long id);
}
