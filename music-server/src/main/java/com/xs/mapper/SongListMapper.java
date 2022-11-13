package com.xs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xs.domain.SongList;
import com.xs.vo.SongListVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xs
 * description 针对表【song_list(歌单)】的数据库操作Mapper
 * createDate 2022-10-09 16:10:47
 * Entity com.xs.domain.SongList
*/
@Repository
@Mapper
public interface SongListMapper extends BaseMapper<SongList> {

    /**
     * 查询所有歌单
     */
    List<SongList> getAllSongList();

    /**
     * 获取歌手总数
     */
    int getSongListCount();

    /**
     * 按歌单类型分组查询的歌单数量
     */
    List<SongListVo> getSongListCountByStyle();

    /**
     * 按歌单风格名称查询所有歌单
     */
    List<SongList> getSongListByStyle(String style);

    /**
     * 获取所有歌单风格
     */
    String[] getAllStyle();

    /**
     * 按风格查询歌单数量
     */
    Integer getAllSongListCountByStyle(String style);
}




