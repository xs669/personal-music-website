package com.xs.service;

import com.xs.common.Result;
import com.xs.domain.SongList;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author xs
 * description 针对表【song_list(歌单)】的数据库操作Service
 * createDate 2022-10-09 16:10:47
*/
public interface SongListService extends IService<SongList> {

    /**
     * 分页查询所有歌单
     */
    Result getAllSongListByPage(Integer currentPage);

    /**
     * 按标题查询歌单
     */
    Result getSongListByName(String name);

    /**
     * 添加歌单
     */
    Result addSongList(SongList songList);

    /**
     * 修改歌单
     */
    Result updateSongList(SongList songList);

    /**
     * 删除歌单
     */
    Result deleteSongList(Long id);

    /**
     * 批量删除歌单
     */
    Result deleteAllSongList(Long[] ids);

    /**
     * 获取歌手总数
     */
    Result getSongListCount();

    /**
     * 按歌单类型分组查询的歌单数量
     */
    Result getSongListCountByStyle();

    /**
     * 按歌单风格名称查询所有歌单
     */
    Result getSongListByStyle(String style);

    /**
     * 查询所有歌单的风格并统计
     */
    Result getAllStyle();

    /**
     * 分页查询所有歌单
     */
    Result getSongListByPage(Integer currentPage);

    /**
     * 查询所有歌单
     */
    Result getAllSongList();

    /**
     * 按歌单风格名称分组查询所有歌单
     */
    Result getSongListByStyleAndPage(String style, Integer currentPage);

    /**
     * 按风格查询歌单数量
     */
    Result getAllSongListCountByStyle(String style);
}
