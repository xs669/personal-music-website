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
import java.util.ArrayList;
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
    private SongMapper songMapper;

    @Resource
    private SingerMapper singerMapper;

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private SongListMapper songListMapper;

    @Resource
    private ListSongMapper listSongMapper;

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
        List<RecentSongVo> recentSongVoList = new ArrayList<>();
        List<RecentSong> recentSongByUserId = recentSongMapper.getRecentSongByUserId(id);
        if (Objects.nonNull(recentSongByUserId) && !recentSongByUserId.isEmpty()) {
            for (RecentSong recentSong : recentSongByUserId) {
                Song song = songMapper.selectById(recentSong.getSongId());
                String singerNameById = singerMapper.getSingerNameById(song.getSingerId());
                RecentSongVo recentSongVo = RecentSongVo.builder()
                        .id(song.getId())
                        .singerId(song.getSingerId())
                        .name(song.getName())
                        .singerName(singerNameById)
                        .introduction(song.getIntroduction())
                        .pic(song.getPic())
                        .lyric(song.getLyric())
                        .url(song.getUrl())
                        .playCount(recentSong.getCount()).build();
                recentSongVoList.add(recentSongVo);
            }
            return Result.ok("查询成功", recentSongVoList);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 推荐歌单
     */
    @Override
    public Result recommendSongList(Long id) {
        HashSet<SongList> songListHashSet = new HashSet<>();
        List<RecentSong> recentSongByUserId = recentSongMapper.getRecentSongByUserId(id);
        List<Collect> allCollectByConsumerId = collectMapper.getAllCollectByConsumerId(id);
        if (Objects.nonNull(allCollectByConsumerId) && !allCollectByConsumerId.isEmpty()) {
            for (Collect collect : allCollectByConsumerId) {
                if (Objects.nonNull(collect.getSongListId())) {
                    songListHashSet.add(songListMapper.selectById(collect.getSongListId()));
                }
            }
        }
        if (Objects.nonNull(recentSongByUserId) && !recentSongByUserId.isEmpty()) {
            for (RecentSong recentSong : recentSongByUserId) {
                Long songId = recentSong.getSongId();
                LambdaQueryWrapper<ListSong> lqw = new LambdaQueryWrapper<>();
                lqw.eq(Objects.nonNull(songId), ListSong::getSongId, songId);
                List<ListSong> listSongs = listSongMapper.selectList(lqw);
                if (Objects.nonNull(listSongs) && !listSongs.isEmpty()) {
                    for (ListSong listSong : listSongs) {
                        Long songListId = listSong.getSongListId();
                        SongList songList = songListMapper.selectById(songListId);
                        songListHashSet.add(songList);
                    }
                }
            }
        }

        if (songListHashSet.size() < 10) {
            List<SongList> allSongList = songListMapper.getAllSongList();
            if (Objects.nonNull(allSongList) && !allSongList.isEmpty()) {
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
        List<RecentSong> recentSongByUserId = recentSongMapper.getRecentSongByUserId(id);
        if (Objects.nonNull(recentSongByUserId) && !recentSongByUserId.isEmpty()) {
            for (RecentSong recentSong : recentSongByUserId) {
                singerHashSet.add(singerMapper.selectById(songMapper.selectById(recentSong.getSongId()).getSingerId()));
            }
        }
        List<Collect> allCollectByConsumerId = collectMapper.getAllCollectByConsumerId(id);
        if (Objects.nonNull(allCollectByConsumerId) && !allCollectByConsumerId.isEmpty()) {
            for (Collect collect : allCollectByConsumerId) {
                Long songId = collect.getSongId();
                if (Objects.nonNull(songId)) {
                    singerHashSet.add(singerMapper.selectById(songMapper.selectById(songId).getSingerId()));
                }
            }
        }
        if (singerHashSet.size() < 10) {
            List<Singer> singerList = singerMapper.selectAllSinger();
            if (Objects.nonNull(singerList) && !singerList.isEmpty()) {
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




