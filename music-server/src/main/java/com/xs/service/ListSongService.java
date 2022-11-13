package com.xs.service;

import com.xs.common.Result;
import com.xs.domain.ListSong;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xs.dto.ListSongByNameDto;
import com.xs.vo.ListSongVo;

/**
 * @author xs
 * description 针对表【list_song(歌单包含歌曲列表)】的数据库操作Service
 * createDate 2022-10-09 22:19:02
*/
public interface ListSongService extends IService<ListSong> {

    /**
     * 查找歌手对应的歌曲名称
     */
    Result getSongNameBySingerId(Long singId);

    /**
     * 查询所有歌单歌曲
     */
    Result getAll(Long id);

    /**
     * 添加歌单歌曲
     */
    Result addListSong(ListSongVo listSongVo);

    /**
     * 删除歌单歌曲
     */
    Result deleteListSong(Long id);

    /**
     * 批量删除歌曲
     */
    Result deleteAllListSong(Long[] ids);

    /**
     * 按名称查询歌单歌曲
     */
    Result getListSongByName(ListSongByNameDto listSongByNameDto);
}
