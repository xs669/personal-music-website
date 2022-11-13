package com.xs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xs.domain.RecentSong;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xs
 * description 针对表【recent_song】的数据库操作Mapper
 * createDate 2022-10-30 10:18:56
 * Entity com.xs.domain.RecentSong
*/
@Mapper
public interface RecentSongMapper extends BaseMapper<RecentSong> {

    /**
     * 查询所有播放记录
     */
    List<RecentSong> getAllRecentSong();

    /**
     * 获取当前用户最近播放列表
     */
    List<RecentSong> getRecentSongByUserId(Long id);

}




