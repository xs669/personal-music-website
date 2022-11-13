package com.xs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.Result;
import com.xs.domain.ListSong;
import com.xs.domain.Singer;
import com.xs.domain.Song;
import com.xs.dto.ListSongByNameDto;
import com.xs.dto.ListSongDto;
import com.xs.mapper.ListSongMapper;
import com.xs.mapper.SingerMapper;
import com.xs.mapper.SongMapper;
import com.xs.service.ListSongService;
import com.xs.vo.ListSongVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xs
 * description 针对表【list_song(歌单包含歌曲列表)】的数据库操作Service实现
 * createDate 2022-10-09 22:19:02
*/
@Service
public class ListSongServiceImpl extends ServiceImpl<ListSongMapper, ListSong> implements ListSongService{

    @Resource
    private ListSongMapper listSongMapper;

    @Resource
    private SongMapper songMapper;

    @Resource
    private SingerMapper singerMapper;

    /**
     * 查找歌手对应的歌曲名称
     */
    @Override
    public Result getSongNameBySingerId(Long singerId) {
        List<ListSongDto> songNameBySingerId = listSongMapper.getSongNameBySingerId(singerId);
        if (Objects.nonNull(songNameBySingerId) && !songNameBySingerId.isEmpty()) {
            return Result.ok("查询成功", songNameBySingerId);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 查询所有歌单歌曲
     */
    @Override
    public Result getAll(Long id) {
        List<ListSongVo> listSongVoList = new ArrayList<>();
        List<ListSong> listSongMapperAll = listSongMapper.getAll(id);
        if (Objects.nonNull(listSongMapperAll) && !listSongMapperAll.isEmpty()) {
            getAllListSong(listSongVoList, listSongMapperAll, songMapper, singerMapper);
        }
        else {
            return Result.error("数据为空");
        }
        return Result.ok("查询成功", listSongVoList);
    }

    /**
     * 添加歌单歌曲
     */
    @Override
    public Result addListSong(ListSongVo listSongVo) {
        Long songId = listSongVo.getSongId();
        Long songListId = listSongVo.getSongListId();
        String singerNameAndsongName = listSongVo.getSingerNameAndsongName();
        List<ListSongVo> listSongVoList = new ArrayList<>();
        List<ListSong> listSongMapperAll = listSongMapper.getAll(songListId);
        if (Objects.nonNull(listSongMapperAll) && !listSongMapperAll.isEmpty()) {
            getAllListSong(listSongVoList, listSongMapperAll, songMapper, singerMapper);
        }
        if (!listSongVoList.isEmpty()) {
            for (ListSongVo songVo : listSongVoList) {
                if (songVo.getSingerNameAndsongName().equals(singerNameAndsongName)) {
                    return Result.error("该歌单歌曲已存在");
                }
            }
        }
        int i = listSongMapper.addListSong(songId, songListId);
        if (i > 0) {
            return Result.ok("添加成功");
        } else {
            return Result.error("添加失败");
        }
    }

    private static void getAllListSong(List<ListSongVo> listSongVoList, List<ListSong> listSongMapperAll, SongMapper songMapper, SingerMapper singerMapper) {
        for (ListSong listSong : listSongMapperAll) {
            ListSongVo listSongVo = new ListSongVo();
            listSongVo.setId(listSong.getId());
            listSongVo.setSongId(listSong.getSongId());
            listSongVo.setSongListId(listSong.getSongListId());
            Song song = songMapper.selectById(listSong.getSongId());
            String songName = song.getName();
            Long singerId = song.getSingerId();
            Singer singer = singerMapper.selectById(singerId);
            String singerName = singer.getName();
            String singerNameAndsongName = singerName + "-" +songName;
            listSongVo.setSingerNameAndsongName(singerNameAndsongName);
            listSongVoList.add(listSongVo);
        }
    }

    /**
     * 删除歌单歌曲
     */
    @Override
    public Result deleteListSong(Long id) {
        int i = listSongMapper.deleteById(id);
        if (i > 0) {
            return Result.ok("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }

    /**
     * 批量删除歌曲
     */
    @Override
    public Result deleteAllListSong(Long[] ids) {
        for (Long id : ids) {
            listSongMapper.deleteById(id);
        }
        return Result.ok("批量删除成功");
    }

    /**
     * 按名称查询歌单歌曲
     */
    @Override
    public Result getListSongByName(ListSongByNameDto listSongByNameDto) {
        List<ListSongVo> songVoList = new ArrayList<>();
        List<ListSongVo> listSongVoList = new ArrayList<>();
        List<ListSong> listSongMapperAll = listSongMapper.getAll(listSongByNameDto.getSongListId());
        if (Objects.nonNull(listSongMapperAll) && !listSongMapperAll.isEmpty()) {
            getAllListSong(listSongVoList, listSongMapperAll, songMapper, singerMapper);
        }
        if (!listSongVoList.isEmpty()) {
            for (ListSongVo listSongVo : listSongVoList) {
                String singerNameAndsongName = listSongVo.getSingerNameAndsongName();
                if (singerNameAndsongName.contains(listSongByNameDto.getName())) {
                    songVoList.add(listSongVo);
                }
            }
        } else {
            return Result.error("数据为空");
        }
        return Result.ok("查询成功", songVoList);
    }
}




