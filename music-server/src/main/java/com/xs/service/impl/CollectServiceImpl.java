package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.Result;
import com.xs.domain.Collect;
import com.xs.domain.Consumer;
import com.xs.domain.Song;
import com.xs.domain.SongList;
import com.xs.dto.CollectDto;
import com.xs.dto.SearchDto;
import com.xs.dto.SongDto;
import com.xs.mapper.*;
import com.xs.service.CollectService;
import com.xs.vo.CollectVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xs
 * description 针对表【collect(收藏)】的数据库操作Service实现
 * createDate 2022-10-11 16:14:36
*/
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService{

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private SongMapper songMapper;

    @Resource
    private SingerMapper singerMapper;

    @Resource
    private SongListMapper songListMapper;

    @Resource
    private ConsumerMapper consumerMapper;

    /**
     * 收藏歌曲
     */
    @Override
    public Result addCollect(Collect collect) {
        int i = collectMapper.insert(collect);
        if (i > 0) {
            return Result.ok("收藏成功");
        } else {
            return Result.error("收藏失败");
        }
    }

    /**
     * 取消收藏
     */
    @Override
    public Result cancelCollect(CollectVo collectVo) {
        LambdaQueryWrapper<Collect> lqw = new LambdaQueryWrapper<>();
        if (collectVo.getType() == 0) {
            lqw.eq(Collect::getUserId, collectVo.getUserId())
                    .eq(Collect::getSongId, collectVo.getSongId());
            int i = collectMapper.delete(lqw);
            if (i > 0) {
                return Result.ok("取消收藏成功");
            } else {
                return Result.error("取消收藏失败");
            }
        } else {
            lqw.eq(Collect::getUserId, collectVo.getUserId())
                    .eq(Collect::getSongListId, collectVo.getSongListId());
            int i = collectMapper.delete(lqw);
            if (i > 0) {
                return Result.ok("取消收藏成功");
            } else {
                return Result.error("取消收藏失败");
            }
        }
    }

    /**
     * 查询歌曲收藏状态
     */
    @Override
    public Result getCollectBySongId(CollectVo collectVo) {
        LambdaQueryWrapper<Collect> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Objects.nonNull(collectVo), Collect::getUserId, collectVo.getUserId())
                .eq(Collect::getSongId, collectVo.getSongId());
        Collect collect = collectMapper.selectOne(lqw);
        if (Objects.nonNull(collect)) {
            return Result.ok("已收藏", true);
        } else {
            return Result.error("暂未收藏");
        }
    }

    /**
     * 获取指定用户所有收藏歌曲
     */
    @Override
    public Result getAllCollectByConsumerId(Long id) {
        List<SongDto> songDtoList = new ArrayList<>();
        List<Collect> allCollectByConsumerId = collectMapper.getAllCollectByConsumerId(id);
        if (Objects.nonNull(allCollectByConsumerId) && !allCollectByConsumerId.isEmpty()) {
            for (Collect collect : allCollectByConsumerId) {
                SongDto songDto = new SongDto();
                Long songId = collect.getSongId();
                if (Objects.nonNull(songId)) {
                    Song song = songMapper.selectById(songId);
                    if (Objects.nonNull(song)) {
                        String singerNameById = singerMapper.getSingerNameById(song.getSingerId());
                        getCollectData(songDtoList, songDto, song, singerNameById);
                    } else {
                        return Result.error("查询失败");
                    }
                }
            }
            return Result.ok("查询成功", songDtoList);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 查询歌单收藏状态
     */
    @Override
    public Result getCollectBySongListId(CollectVo collectVo) {
        LambdaQueryWrapper<Collect> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Objects.nonNull(collectVo), Collect::getUserId, collectVo.getUserId())
                .eq(Collect::getSongListId, collectVo.getSongListId());
        Collect collect = collectMapper.selectOne(lqw);
        if (Objects.nonNull(collect)) {
            return Result.ok("已收藏", true);
        } else {
            return Result.error("暂未收藏");
        }
    }

    /**
     * 获取指定用户所有收藏歌单
     */
    @Override
    public Result getAllCollectSongListByUserId(Long id) {
        List<SongList> songLists = new ArrayList<>();
        List<Collect> allCollectByConsumerId = collectMapper.getAllCollectByConsumerId(id);
        if (Objects.nonNull(allCollectByConsumerId) && !allCollectByConsumerId.isEmpty()) {
            for (Collect collect : allCollectByConsumerId) {
                Long songListId = collect.getSongListId();
                if (Objects.nonNull(songListId)) {
                    SongList songList = songListMapper.selectById(songListId);
                    songLists.add(songList);
                }
            }
            return Result.ok("查询成功", songLists);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 获取指定用户收藏信息
     */
    @Override
    public Result getAllCollectByUserId(Long id) {
        List<CollectDto> collectDtoList = new ArrayList<>();
        List<Collect> allCollectByConsumerId = collectMapper.getAllCollectByConsumerId(id);
        if (Objects.nonNull(allCollectByConsumerId) && !allCollectByConsumerId.isEmpty()) {
            getCollectList(collectDtoList, allCollectByConsumerId, consumerMapper, songMapper, songListMapper);
            return Result.ok("查询成功", collectDtoList);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 删除指定用户收藏数据
     */
    @Override
    public Result delCollect(Long id) {
        int i = collectMapper.deleteById(id);
        if (i > 0) {
            return Result.ok("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }

    /**
     * 批量删除指定用户收藏数据
     */
    @Override
    public Result delAllCollect(Long[] ids) {
        if (Objects.nonNull(ids)) {
            for (Long id : ids) {
                collectMapper.deleteById(id);
            }
            return Result.ok("批量删除成功");
        } else {
            return Result.error("批量删除失败");
        }
    }

    /**
     * 模糊搜索指定用户收藏信息
     */
    @Override
    public Result searchCollectByUserId(SearchDto searchDto) {
        List<CollectDto> collectDtoList = new ArrayList<>();
        List<CollectDto> collectDtos = new ArrayList<>();
        List<Collect> allCollectByConsumerId = collectMapper.getAllCollectByConsumerId(searchDto.getId());
        if (Objects.nonNull(allCollectByConsumerId) && !allCollectByConsumerId.isEmpty()) {
            getCollectList(collectDtoList, allCollectByConsumerId, consumerMapper, songMapper, songListMapper);
            for (CollectDto collectDto : collectDtoList) {
                if (collectDto.getContent().contains(searchDto.getKeyWord())) {
                    collectDtos.add(collectDto);
                }
            }
            return Result.ok("查询成功", collectDtos);
        } else {
            return Result.error("查询失败");
        }
    }

    private static void getCollectList(List<CollectDto> collectDtoList, List<Collect> allCollectByConsumerId, ConsumerMapper consumerMapper, SongMapper songMapper, SongListMapper songListMapper) {
        for (Collect collect : allCollectByConsumerId) {
            Consumer consumer = consumerMapper.selectById(collect.getUserId());
            if (Objects.nonNull(collect.getSongId())) {
                Song song = songMapper.selectById(collect.getSongId());
                CollectDto collectDto = CollectDto.builder()
                        .id(collect.getId())
                        .username(consumer.getUsername())
                        .avatar(consumer.getAvatar())
                        .content("歌曲: " + song.getName())
                        .createTime(collect.getCreateTime()).build();
                collectDtoList.add(collectDto);
            }
            if (Objects.nonNull(collect.getSongListId())) {
                SongList songList = songListMapper.selectById(collect.getSongListId());
                CollectDto collectDto = CollectDto.builder()
                        .id(collect.getId())
                        .username(consumer.getUsername())
                        .avatar(consumer.getAvatar())
                        .content("歌单: " + songList.getTitle())
                        .createTime(collect.getCreateTime()).build();
                collectDtoList.add(collectDto);
            }
        }
    }

    public static void getCollectData(List<SongDto> songDtoList, SongDto songDto, Song song, String singerNameById) {
        songDto.setId(song.getId());
        songDto.setSingerId(song.getSingerId());
        songDto.setName(song.getName());
        songDto.setSingerName(singerNameById);
        songDto.setIntroduction(song.getIntroduction());
        songDto.setCreateTime(song.getCreateTime());
        songDto.setUpdateTime(song.getUpdateTime());
        songDto.setPic(song.getPic());
        songDto.setLyric(song.getLyric());
        songDto.setUrl(song.getUrl());
        songDto.setPlayCount(song.getPlayCount());
        songDtoList.add(songDto);
    }
}




