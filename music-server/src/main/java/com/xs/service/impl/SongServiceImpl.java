package com.xs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.Result;
import com.xs.domain.Song;
import com.xs.dto.SongDto;
import com.xs.mapper.CollectMapper;
import com.xs.mapper.CommentMapper;
import com.xs.mapper.SongMapper;
import com.xs.service.SongService;
import com.xs.vo.SongVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
        List<SongDto> allBySongName = songMapper.getAllBySongName(name);
        return Result.ok("查询成功", allBySongName);
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
        List<SongDto> allSongBySingerId = songMapper.getAllSongBySingerId(id);
        return Result.ok("查询成功", allSongBySingerId);
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
        List<SongDto> allSong = songMapper.getAllSong();
        return Result.ok("查询成功", allSong);
    }

    /**
     * 模糊查询某个歌手所属全部歌曲
     */
    @Override
    public Result getAllSongByNameAndSingerId(SongVo songVo) {
        List<SongDto> allSongByNameAndSingerId = songMapper.getAllSongByNameAndSingerId(songVo.getId(), songVo.getName());
        return Result.ok("查询成功", allSongByNameAndSingerId);
    }

    /**
     * 按歌单id查询所有歌曲
     */
    @Override
    public Result getAllSongBySongListId(Long id) {
        List<SongDto> allSongBySongListId = songMapper.getAllSongBySongListId(id);
        return Result.ok("查询成功", allSongBySongListId);
    }

    /**
     * 按歌手id查询所有歌曲和歌手名
     */
    @Override
    public Result gerAllSongAndSingerNameBySingerId(Long id) {
        List<SongDto> allSongBySingerId = songMapper.getAllSongBySingerId(id);
        return Result.ok("查询成功", allSongBySingerId);
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
        List<SongDto> songByPlayCount = songMapper.getSongByPlayCount();
        return Result.ok("查询成功", songByPlayCount);
    }
}




