package com.xs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xs.domain.RecentSong;
import com.xs.domain.Singer;
import com.xs.domain.SongList;
import com.xs.vo.RecentSongVo;
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
    List<RecentSongVo> getRecentSongByUserId(Long id);

    /**
     * 由最近播放歌曲查询其歌单信息
     */
    List<SongList> getSongListByRecentSong(Long userId);

    /**
     * 由最近播放歌曲查询其歌手信息
     */
    List<Singer> getSingerByRecentSong(Long userId);

}




