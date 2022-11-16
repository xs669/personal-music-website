package com.xs.mapper;

import com.xs.domain.ListSong;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xs.dto.ListSongDto;
import com.xs.vo.ListSongVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xs
 * description 针对表【list_song(歌单包含歌曲列表)】的数据库操作Mapper
 * createDate 2022-10-09 22:19:02
 * Entity com.xs.domain.ListSong
*/
@Repository
@Mapper
public interface ListSongMapper extends BaseMapper<ListSong> {

    /**
     * 查找歌手对应的歌曲名称
     */
    List<ListSongDto> getSongNameBySingerId(Long singerId);

    /**
     * 查询所有歌单歌曲
     */
    List<ListSong> getAll(Long id);

    /**
     * 添加歌单歌曲
     */
    int addListSong(Long songId, Long songListId);

    /**
     * 按歌单id删除歌单歌曲信息
     */
    int deleteBySongListId(Long id);

    /**
     * 查询某个歌单中的歌曲信息
     */
    List<ListSongVo> getAllListSongBySongListId(Long songListId);

}




