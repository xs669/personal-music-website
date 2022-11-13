package com.xs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.Result;
import com.xs.domain.ListSong;
import com.xs.domain.Song;
import com.xs.dto.SongDto;
import com.xs.mapper.*;
import com.xs.service.SongService;
import com.xs.vo.SongVo;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xs
 * description 针对表【song(歌曲)】的数据库操作Service实现
 * createDate 2022-10-05 15:00:05
*/
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService{

    @Resource
    private ListSongMapper listSongMapper;

    @Resource
    private SingerMapper singerMapper;

    @Resource
    private SongMapper songMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private CollectMapper collectMapper;

    /**
     * 添加歌曲
     */
    @Override
    public Result addSong(Song song) {
        if (Objects.isNull(song)) {
            return Result.error("数据为空,添加失败！");
        } else {
            int insert = songMapper.insert(song);
            if (insert > 0) {
                return Result.ok("添加成功");
            } else {
                return Result.error("添加失败");
            }
        }
    }

    /**
     * 更新歌曲
     */
    @Override
    public Result updateSong(Song song) {
        Song song1 = songMapper.selectById(song.getId());
        if (!song.equals(song1)) {
            song.setUpdateTime(LocalDateTime.now());
            int i = songMapper.updateById(song);
            if (i > 0) {
                return Result.ok("更新成功");
            } else {
                return Result.error("更新失败");
            }
        } else {
            return Result.error("修改信息没有变化");
        }
    }

    /**
     * 按歌名查询所有歌曲
     */
    @Override
    public Result getAllSongByName(String name) {
        List<SongDto> songDtoList = new ArrayList<>();
        List<Song> songList = songMapper.getAllBySongName(name);
        return getResult(songDtoList, songList);
    }

    /**
     * 按id删除歌曲
     */
    @Override
    public Result deleteSong(Long id) {
        collectMapper.deleteBySongId(id);
        commentMapper.deleteBySongId(id);
        int i = songMapper.deleteById(id);
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
    public Result deleteAllSong(Long[] ids) {
        for (Long id : ids) {
            collectMapper.deleteBySongId(id);
            commentMapper.deleteBySongId(id);
            songMapper.deleteById(id);
        }
        return Result.ok("批量删除成功");
    }

    /**
     * 按歌手id查询所有歌曲
     */
    @Override
    public Result getAllSongBySingId(Long id) {
        List<Song> allSongBySingerId = songMapper.getAllSongBySingerId(id);
        if (Objects.nonNull(allSongBySingerId) && !allSongBySingerId.isEmpty()) {
            return Result.ok("查询成功", allSongBySingerId);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 获取歌曲总数
     */
    @Override
    public Result getSongCount() {
        int songCount = songMapper.getSongCount();
        return Result.ok("查询成功", songCount);
    }

    /**
     * 查询所有歌曲
     */
    @Override
    public Result getAllSong() {
        List<SongDto> songDtoList = new ArrayList<>();
        List<Song> allSong = songMapper.getAllSong();
        return getResult(songDtoList, allSong);
    }

    /**
     * 模糊查询某个歌手所属全部歌曲
     */
    @Override
    public Result getAllSongByNameAndSingerId(SongVo songVo) {
        Long id = songVo.getId();
        String name = songVo.getName();
        List<Song> allSongByNameAndSingerId = songMapper.getAllSongByNameAndSingerId(id, name);
        if (Objects.nonNull(allSongByNameAndSingerId) && !allSongByNameAndSingerId.isEmpty()) {
            return Result.ok("查询成功", allSongByNameAndSingerId);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 按歌单id查询所有歌曲
     */
    @Override
    public Result getAllSongBySongListId(Long id) {
        List<SongDto> songDtoList = new ArrayList<>();
        List<ListSong> listSongList = listSongMapper.getAll(id);
        if (Objects.nonNull(listSongList) && !listSongList.isEmpty()) {
            for (ListSong listSong : listSongList) {
                Long songId = listSong.getSongId();
                Song song = songMapper.selectById(songId);
                if (Objects.nonNull(song)) {
                    getSongResult(songDtoList, song);
                } else {
                    return Result.error("查询失败");
                }
            }
            return Result.ok("查询成功", songDtoList);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 按歌手id查询所有歌曲和歌手名
     */
    @Override
    public Result gerAllSongAndSingerNameBySingerId(Long id) {
        List<SongDto> songDtoList = new ArrayList<>();
        List<Song> allSongBySingerId = songMapper.getAllSongBySingerId(id);
        if (Objects.nonNull(allSongBySingerId) && !allSongBySingerId.isEmpty()) {
            for (Song song : allSongBySingerId) {
                SongDto songDto = new SongDto();
                String singerNameById = singerMapper.getSingerNameById(song.getSingerId());
                getSongList(songDtoList, song, songDto, singerNameById);
            }
            return Result.ok("查询成功", songDtoList);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 增加播放量
     */
    @Override
    public Result addPlayCount(SongVo songVo) {
        Song song = songMapper.selectById(songVo.getId());
        song.setPlayCount(song.getPlayCount() + 1);
        song.setUpdateTime(LocalDateTime.now());
        int i = songMapper.updateById(song);
        if (i > 0) {
            return Result.ok("增加成功");
        } else {
            return Result.error("增加失败");
        }
    }

    /**
     * 按播放量排行
     */
    @Override
    public Result getSongByPlayCount() {
        List<SongDto> songDtoList = new ArrayList<>();
        List<Song> songByPlayCount = songMapper.getSongByPlayCount();
        return getResult(songDtoList, songByPlayCount);
    }

    private void getSongList(List<SongDto> songDtoList, Song song, SongDto songDto, String singerNameById) {
        CollectServiceImpl.getCollectData(songDtoList, songDto, song, singerNameById);
    }

    private void getSongResult(List<SongDto> songDtoList, Song song) {
        SongDto songDto = new SongDto();
        Long singerId = song.getSingerId();
        String singerNameById = singerMapper.getSingerNameById(singerId);
        getSongList(songDtoList, song, songDto, singerNameById);
    }

    @NotNull
    private Result getResult(List<SongDto> songDtoList, List<Song> allSong) {
        if (Objects.nonNull(allSong) && !allSong.isEmpty()) {
            for (Song song : allSong) {
                getSongResult(songDtoList, song);
            }
            return Result.ok("查询成功", songDtoList);
        } else {
            return Result.error("查询失败");
        }
    }
}




