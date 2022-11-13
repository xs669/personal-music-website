package com.xs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xs.domain.Song;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xs
 * description 针对表【song(歌曲)】的数据库操作Mapper
 * createDate 2022-10-05 15:00:05
 * Entity com.xs.domain.Song
*/
@Repository
@Mapper
public interface SongMapper extends BaseMapper<Song> {

    /**
     * 按歌手id查询歌曲信息
     */
    List<Song> getAllSongBySingerId(Long id);

    /**
     * 按歌手id删除歌曲信息
     */
    int deleteBySingerId(Long id);

    /**
     * 获取歌曲总数
     */
    int getSongCount();

    /**
     * 查询所有歌曲
     */
    List<Song> getAllSong();

    /**
     * 模糊查询所有歌曲
     */
    List<Song> getAllBySongName(String name);

    /**
     * 模糊查询某个歌手所属全部歌曲
     */
    List<Song> getAllSongByNameAndSingerId(Long id, String name);

    /**
     * 按播放量排行
     */
    List<Song> getSongByPlayCount();

}




