package com.xs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.Result;
import com.xs.domain.ListSong;
import com.xs.dto.ListSongByNameDto;
import com.xs.dto.ListSongDto;
import com.xs.mapper.ListSongMapper;
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
        List<ListSongVo> allListSongBySongListId = listSongMapper.getAllListSongBySongListId(id);
        return Result.ok("查询成功", allListSongBySongListId);
    }

    /**
     * 添加歌单歌曲
     */
    @Override
    public Result addListSong(ListSongVo listSongVo) {
        List<ListSongVo> allListSongBySongListId = listSongMapper.getAllListSongBySongListId(listSongVo.getSongListId());
        if (!allListSongBySongListId.isEmpty()) {
            for (ListSongVo songVo : allListSongBySongListId) {
                if (songVo.getSingerNameAndsongName().equals(listSongVo.getSingerNameAndsongName())) {
                    return Result.error("该歌单歌曲已存在");
                }
            }
        }
        int i = listSongMapper.addListSong(listSongVo.getSongId(), listSongVo.getSongListId());
        if (i > 0) {
            return Result.ok("添加成功");
        } else {
            return Result.error("添加失败");
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
        List<ListSongVo> allListSongBySongListId = listSongMapper.getAllListSongBySongListId(listSongByNameDto.getSongListId());
        List<ListSongVo> listSongVoList = new ArrayList<>();
        if (!allListSongBySongListId.isEmpty()) {
            for (ListSongVo listSongVo : allListSongBySongListId) {
                if (listSongVo.getSingerNameAndsongName().contains(listSongByNameDto.getName())) {
                    listSongVoList.add(listSongVo);
                }
            }
        }
        return Result.ok("查询成功", listSongVoList);
    }
}




