package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.Result;
import com.xs.domain.Collect;
import com.xs.domain.SongList;
import com.xs.dto.CollectDto;
import com.xs.dto.SearchDto;
import com.xs.dto.SongDto;
import com.xs.mapper.CollectMapper;
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
        List<SongDto> allCollectByConsumerId = collectMapper.getAllCollectSongByConsumerId(id);
        return Result.ok("查询成功", allCollectByConsumerId);
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
        List<SongList> allCollectSongListByConsumerId = collectMapper.getAllCollectSongListByConsumerId(id);
        return Result.ok("查询成功", allCollectSongListByConsumerId);
    }

    /**
     * 获取指定用户收藏信息
     */
    @Override
    public Result getAllCollectByUserId(Long id) {
        List<CollectDto> allCollectSongByUserId = collectMapper.getAllCollectSongByUserId(id);
        List<CollectDto> allCollectSongListByUserId = collectMapper.getAllCollectSongListByUserId(id);
        List<CollectDto> collectDtoList = new ArrayList<>(allCollectSongByUserId);
        collectDtoList.addAll(allCollectSongListByUserId);
        return Result.ok("查询成功", collectDtoList);
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
        List<CollectDto> allCollectSongByUserId = collectMapper.getAllCollectSongByUserId(searchDto.getId());
        List<CollectDto> allCollectSongListByUserId = collectMapper.getAllCollectSongListByUserId(searchDto.getId());
        List<CollectDto> collectDtoList = new ArrayList<>(allCollectSongByUserId);
        collectDtoList.addAll(allCollectSongListByUserId);
        List<CollectDto> collectDtos = new ArrayList<>();
        for (CollectDto collectDto : collectDtoList) {
            if (collectDto.getContent().contains(searchDto.getKeyWord()) || collectDto.getUsername().contains(searchDto.getKeyWord())) {
                collectDtos.add(collectDto);
            }
        }
        return Result.ok("查询成功", collectDtos);
    }

}




