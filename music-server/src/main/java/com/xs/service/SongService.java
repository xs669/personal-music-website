package com.xs.service;

import com.xs.common.Result;
import com.xs.domain.Song;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xs.vo.SongVo;

/**
 * @author xs
 * description 针对表【song(歌曲)】的数据库操作Service
 * createDate 2022-10-05 15:00:05
*/
public interface SongService extends IService<Song> {

    /**
     * 添加歌曲
     */
    Result addSong(Song song);

    /**
     * 更新歌曲
     */
    Result updateSong(Song song);

    /**
     * 按歌名查询歌曲
     */
    Result getAllSongByName(String name);

    /**
     * 按id删除歌曲
     */
    Result deleteSong(Long id);

    /**
     * 批量删除歌曲
     */
    Result deleteAllSong(Long[] ids);

    /**
     * 按歌手id查询所有歌曲
     */
    Result getAllSongBySingId(Long id);

    /**
     * 获取歌曲总数
     */
    Result getSongCount();

    /**
     * 查询所有歌曲
     */
    Result getAllSong();

    /**
     * 模糊查询某个歌手所属全部歌曲
     */
    Result getAllSongByNameAndSingerId(SongVo songVo);

    /**
     * 按歌单id查询所有歌曲
     */
    Result getAllSongBySongListId(Long id);

    /**
     * 按歌手id查询所有歌曲和歌手名
     */
    Result gerAllSongAndSingerNameBySingerId(Long id);

    /**
     * 增加播放量
     */
    Result addPlayCount(SongVo songVo);

    /**
     * 按播放量排行
     */
    Result getSongByPlayCount();
}
