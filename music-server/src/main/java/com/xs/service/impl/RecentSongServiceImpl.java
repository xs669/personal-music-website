package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.Result;
import com.xs.domain.*;
import com.xs.mapper.*;
import com.xs.service.RecentSongService;
import com.xs.vo.RecentSongVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * @author xs
 * description 针对表【recent_song】的数据库操作Service实现
 * createDate 2022-10-30 10:18:56
*/
@Service
public class RecentSongServiceImpl extends ServiceImpl<RecentSongMapper, RecentSong> implements RecentSongService{

    @Resource
    private RecentSongMapper recentSongMapper;

    @Resource
    private SingerMapper singerMapper;

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private SongListMapper songListMapper;

    /**
     * 添加歌曲最近播放记录
     */
    @Override
    public Result addRecentSong(RecentSong recentSong) {
        LambdaQueryWrapper<RecentSong> lqw = new LambdaQueryWrapper<>();
        lqw.eq(RecentSong::getUserId, recentSong.getUserId())
                .eq(RecentSong::getSongId, recentSong.getSongId());
        RecentSong song = recentSongMapper.selectOne(lqw);
        if (Objects.nonNull(song)) {
            song.setCount(song.getCount() + 1);
            song.setUpdateTime(LocalDateTime.now());
            int i = recentSongMapper.updateById(song);
            if (i > 0) {
                return Result.ok("添加成功");
            } else {
                return Result.error("添加失败");
            }
        } else {
            int i = recentSongMapper.insert(recentSong);
            if (i > 0) {
                return Result.ok("添加成功");
            } else {
                return Result.error("添加失败");
            }
        }
    }

    /**
     * 获取当前用户最近播放列表
     */
    @Override
    public Result getRecentSongByUserId(Long id) {
        List<RecentSongVo> recentSongByUserId = recentSongMapper.getRecentSongByUserId(id);
        return Result.ok("查询成功", recentSongByUserId);
    }

    /**
     * 推荐歌单
     */
    @Override
    public Result recommendSongList(Long id) {
        HashSet<SongList> songListHashSet = new HashSet<>();
        List<SongList> songListByRecentSong = recentSongMapper.getSongListByRecentSong(id);
        List<SongList> allCollectSongListByConsumerId = collectMapper.getAllCollectSongListByConsumerId(id);
        if (!allCollectSongListByConsumerId.isEmpty()) {
            songListHashSet.addAll(allCollectSongListByConsumerId);
        }
        if (!songListByRecentSong.isEmpty()) {
            songListHashSet.addAll(songListByRecentSong);
        }
        if (songListHashSet.size() < 10) {
            List<SongList> allSongList = songListMapper.getAllSongList();
            if (!allSongList.isEmpty()) {
                for (SongList songList : allSongList) {
                    if (songListHashSet.size() == 10) {
                        break;
                    }
                    songListHashSet.add(songList);
                }
            }
        }
        return Result.ok("查询成功", songListHashSet);
    }

    /**
     * 推荐歌手
     */
    @Override
    public Result recommendSinger(Long id) {
        HashSet<Singer> singerHashSet = new HashSet<>();
        List<Singer> singerByRecentSong = recentSongMapper.getSingerByRecentSong(id);
        if (!singerByRecentSong.isEmpty()) {
            singerHashSet.addAll(singerByRecentSong);
        }
        List<Singer> singerByCollectSong = collectMapper.getSingerByCollectSong(id);
        if (!singerByCollectSong.isEmpty()) {
            singerHashSet.addAll(singerByCollectSong);
        }
        if (singerHashSet.size() < 10) {
            List<Singer> singerList = singerMapper.selectAllSinger();
            if (!singerList.isEmpty()) {
                for (Singer singer : singerList) {
                    if (singerHashSet.size() == 10) {
                        break;
                    }
                    singerHashSet.add(singer);
                }
            }
        }
        return Result.ok("查询成功", singerHashSet);
    }
}




