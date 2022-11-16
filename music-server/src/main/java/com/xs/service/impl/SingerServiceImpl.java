package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.Result;
import com.xs.domain.Singer;
import com.xs.dto.SingerDto;
import com.xs.mapper.SingerMapper;
import com.xs.mapper.SongMapper;
import com.xs.service.SingerService;
import com.xs.vo.SingerVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author xs
 * description 针对表【singer(歌手)】的数据库操作Service实现
 * createDate 2022-10-05 15:08:13
*/
@Service
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements SingerService{

    @Resource
    private SingerMapper singerMapper;

    @Resource
    private SongMapper songMapper;

    private static final Integer pageSize = 4;
    /**
     * 添加歌手
     */
    @Override
    public Result addSinger(Singer singer) {
        List<Singer> singerList = singerMapper.selectAllSinger();
        if (!singerList.isEmpty()) {
            for (Singer s : singerList) {
                if (s.getName().equals(singer.getName())) {
                    return Result.error("歌手名不能重复");
                }
            }
        }
        int i = singerMapper.insert(singer);
        if (i > 0) {
            return Result.ok("添加成功");
        } else {
            return Result.error("添加失败");
        }
    }

    /**
     * 修改歌手
     */
    @Override
    public Result updateSinger(Singer singer) {
        Singer singer1 = singerMapper.selectById(singer.getId());
        if (!singer.equals(singer1)) {
            int i = singerMapper.updateById(singer);
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
     * 删除歌手
     */
    @Override
    public Result deleteSinger(Long id) {
        songMapper.deleteBySingerId(id);
        int i = singerMapper.deleteById(id);
        if (i > 0) {
            return Result.ok("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }

    /**
     * 分页查询所有歌手
     */
    @Override
    public Result getAllSingerByPage(Integer currentPage) {
        IPage<Singer> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Singer> lqw = new LambdaQueryWrapper<>();
        IPage<Singer> singerIPage = singerMapper.selectPage(page, lqw);
        List<Singer> records = singerIPage.getRecords();
        if (!records.isEmpty()) {
            return Result.ok("查询成功", records);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 按名字模糊查询歌手
     */
    @Override
    public Result singerOfName(String name) {
        LambdaQueryWrapper<Singer> lqw = new LambdaQueryWrapper<>();
        lqw.like(Objects.nonNull(name), Singer::getName, name);
        List<Singer> singerList = singerMapper.selectList(lqw);
        if (!singerList.isEmpty()) {
            return Result.ok("查询成功", singerList);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 批量删除歌手
     */
    @Override
    public Result deleteAllSinger(Long[] ids) {
        for (Long id : ids) {
            songMapper.deleteBySingerId(id);
            singerMapper.deleteById(id);
        }
        return Result.ok("批量删除成功");
    }

    /**
     * 获取歌手总数
     */
    @Override
    public Result getSingerCount() {
        int singerCount = singerMapper.getSingerCount();
        return Result.ok("查询成功", singerCount);
    }

    /**
     * 按歌手id查询歌手名
     */
    @Override
    public Result getSingerNameById(Long id) {
        String singerNameById = singerMapper.getSingerNameById(id);
        if (Objects.nonNull(singerNameById)) {
            return Result.ok("查询成功", singerNameById);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 按地区分组查询歌手数量
     */
    @Override
    public Result getSingerCountByLocation() {
        List<SingerVo> singerCountByLocation = singerMapper.getSingerCountByLocation();
        if (!singerCountByLocation.isEmpty()) {
            return Result.ok("查询成功", singerCountByLocation);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 按性别分组查询歌手数量
     */
    @Override
    public Result getSingerCountBySex() {
        List<SingerDto> singerCountBySex = singerMapper.getSingerCountBySex();
        if (!singerCountBySex.isEmpty()) {
            return Result.ok("查询成功", singerCountBySex);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 按性别查询所有歌手
     */
    @Override
    public Result getAllSingerBySex(Integer sex) {
        LambdaQueryWrapper<Singer> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Objects.nonNull(sex), Singer::getSex, sex);
        List<Singer> singerList = singerMapper.selectList(lqw);
        if (!singerList.isEmpty()) {
            return Result.ok("查询成功", singerList);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 按id查询歌手
     */
    @Override
    public Result getSingerById(Long id) {
        Singer singer = singerMapper.selectById(id);
        if (Objects.nonNull(singer)) {
            return Result.ok("查询成功");
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 查询所有歌手
     */
    @Override
    public Result getAllSinger() {
        List<Singer> singerList = singerMapper.selectAllSinger();
        if (!singerList.isEmpty()) {
            return Result.ok("查询成功", singerList);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 分页查询所有歌手
     */
    @Override
    public Result getSingerByPage(Integer currentPage) {
        IPage<Singer> page = new Page<>(currentPage, 10);
        LambdaQueryWrapper<Singer> lqw = new LambdaQueryWrapper<>();
        IPage<Singer> singerIPage = singerMapper.selectPage(page, lqw);
        List<Singer> records = singerIPage.getRecords();
        if (!records.isEmpty()) {
            return Result.ok("查询成功", records);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 按性别分页查询所有歌手
     */
    @Override
    public Result getAllSingerBySexAndPage(Integer sex, Integer currentPage) {
        IPage<Singer> page = new Page<>(currentPage, 10);
        LambdaQueryWrapper<Singer> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Objects.nonNull(sex), Singer::getSex, sex);
        IPage<Singer> singerIPage = singerMapper.selectPage(page, lqw);
        List<Singer> records = singerIPage.getRecords();
        if (!records.isEmpty()) {
            return Result.ok("查询成功", records);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 按性别查询歌手数量
     */
    @Override
    public Result getAllSingerCountBySex(Integer sex) {
        Integer allSingerCountBySex = singerMapper.getAllSingerCountBySex(sex);
        return Result.ok("查询成功", allSingerCountBySex);
    }
}




