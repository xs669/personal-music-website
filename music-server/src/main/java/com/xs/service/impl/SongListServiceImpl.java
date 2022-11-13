package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.Result;
import com.xs.domain.SongList;
import com.xs.mapper.*;
import com.xs.service.SongListService;
import com.xs.vo.SongListVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xs
 * description 针对表【song_list(歌单)】的数据库操作Service实现
 * createDate 2022-10-09 16:10:47
*/
@Service
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements SongListService{

    @Resource
    private SongListMapper songListMapper;

    @Resource
    private ListSongMapper listSongMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private RanksMapper ranksMapper;

    private static final Map<String, Integer> map = new HashMap<>();

    /**
     * 查询所有歌单
     */
    @Override
    public Result getAllSongListByPage(Integer currentPage) {
        IPage<SongList> page = new Page<>(currentPage, 4);
        LambdaQueryWrapper<SongList> lqw = new LambdaQueryWrapper<>();
        IPage<SongList> songListIPage = songListMapper.selectPage(page, lqw);
        List<SongList> records = songListIPage.getRecords();
        if (Objects.nonNull(records) && !records.isEmpty()) {
            return Result.ok("查询成功", records);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 按标题查询歌单
     */
    @Override
    public Result getSongListByName(String name) {
        LambdaQueryWrapper<SongList> lqw = new LambdaQueryWrapper<>();
        lqw.like(Objects.nonNull(name), SongList::getTitle, name);
        List<SongList> songLists = songListMapper.selectList(lqw);
        if (Objects.nonNull(songLists) && !songLists.isEmpty()) {
            return Result.ok("查询成功", songLists);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 添加歌单
     */
    @Override
    public Result addSongList(SongList songList) {
        int i = songListMapper.insert(songList);
        if (i > 0) {
            return Result.ok("添加成功", songList);
        } else {
            return Result.error("添加失败");
        }
    }

    /**
     * 修改歌单
     */
    @Override
    public Result updateSongList(SongList songList) {
        SongList songList1 = songListMapper.selectById(songList.getId());
        if (!songList.equals(songList1)) {
            int i = songListMapper.updateById(songList);
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
     * 删除歌单
     */
    @Override
    public Result deleteSongList(Long id) {
        commentMapper.deleteBySongListId(id);
        collectMapper.deleteBySongListId(id);
        ranksMapper.deleteBySongListId(id);
        listSongMapper.deleteBySongListId(id);
        int i = songListMapper.deleteById(id);
        if (i > 0) {
            return Result.ok("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }

    /**
     * 批量删除歌单
     */
    @Override
    public Result deleteAllSongList(Long[] ids) {
        for (Long id : ids) {
            commentMapper.deleteBySongListId(id);
            collectMapper.deleteBySongListId(id);
            ranksMapper.deleteBySongListId(id);
            listSongMapper.deleteBySongListId(id);
            songListMapper.deleteById(id);
        }
        return Result.ok("批量删除成功");
    }

    /**
     * 获取歌手总数
     */
    @Override
    public Result getSongListCount() {
        int songListCount = songListMapper.getSongListCount();
        return Result.ok("查询成功", songListCount);
    }

    /**
     * 按歌单类型分组查询的歌单数量
     */
    @Override
    public Result getSongListCountByStyle() {
        List<SongListVo> songListCountByStyle = songListMapper.getSongListCountByStyle();
        if (Objects.nonNull(songListCountByStyle) && !songListCountByStyle.isEmpty()) {
            return Result.ok("查询成功", songListCountByStyle);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 按歌单风格名称查询所有歌单
     */
    @Override
    public Result getSongListByStyle(String style) {
        List<SongList> songListByStyle = songListMapper.getSongListByStyle(style);
        if (Objects.nonNull(songListByStyle) && !songListByStyle.isEmpty()) {
            return Result.ok("查询成功", songListByStyle);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 查询所有歌单的风格并统计
     */
    @Override
    public Result getAllStyle() {
        List<SongListVo> songListVoList = new ArrayList<>();
        ArrayList<String> arrayList = new ArrayList<>();
        String[] allStyle = songListMapper.getAllStyle();
        if (Objects.nonNull(allStyle)) {
            for (String s : allStyle) {
                if (s.contains("，")) {
                    String[] split = s.split("，");
                    if (split.length == 3) {
                        arrayList.add(split[0]);
                        arrayList.add(split[1]);
                        arrayList.add(split[2]);
                        continue;
                    }
                    if (split.length == 2) {
                        arrayList.add(split[0]);
                        arrayList.add(split[1]);
                        continue;
                    }
                }
                if (s.contains("、")) {
                    String[] split = s.split("、");
                    if (split.length == 3) {
                        arrayList.add(split[0]);
                        arrayList.add(split[1]);
                        arrayList.add(split[2]);
                        continue;
                    }
                    if (split.length == 2) {
                        arrayList.add(split[0]);
                        arrayList.add(split[1]);
                        continue;
                    }
                }
                arrayList.add(s);
            }
            getCount(arrayList);
            List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
            //降序排序
            list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            int sort = 1;
            for (Map.Entry<String, Integer> newEntry : list) {
                // 取出排名前7的值
                if (sort <= 7) {
                    SongListVo songListVo = SongListVo.builder()
                            .style(newEntry.getKey()).number(newEntry.getValue())
                            .build();
                    songListVoList.add(songListVo);
                    ++sort;
                }
            }
            return Result.ok("查询成功", songListVoList);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 分页查询所有歌单
     */
    @Override
    public Result getSongListByPage(Integer currentPage) {
        IPage<SongList> page = new Page<>(currentPage, 10);
        LambdaQueryWrapper<SongList> lqw = new LambdaQueryWrapper<>();
        IPage<SongList> songListIPage = songListMapper.selectPage(page, lqw);
        List<SongList> records = songListIPage.getRecords();
        if (Objects.nonNull(records) && !records.isEmpty()) {
            return Result.ok("查询成功", records);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 查询所有歌单
     */
    @Override
    public Result getAllSongList() {
        List<SongList> allSongList = songListMapper.getAllSongList();
        if (Objects.nonNull(allSongList) && !allSongList.isEmpty()) {
            return Result.ok("查询成功", allSongList);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 按歌单风格名称分组查询所有歌单
     */
    @Override
    public Result getSongListByStyleAndPage(String style, Integer currentPage) {
        IPage<SongList> page = new Page<>(currentPage, 10);
        LambdaQueryWrapper<SongList> lqw = new LambdaQueryWrapper<>();
        lqw.like(Objects.nonNull(style), SongList::getStyle, style);
        IPage<SongList> songListIPage = songListMapper.selectPage(page, lqw);
        List<SongList> records = songListIPage.getRecords();
        if (Objects.nonNull(records) && !records.isEmpty()) {
            return Result.ok("查询成功", records);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 按风格查询歌单数量
     */
    @Override
    public Result getAllSongListCountByStyle(String style) {
        Integer allSongListCountByStyle = songListMapper.getAllSongListCountByStyle(style);
        return Result.ok("查询成功", allSongListCountByStyle);
    }

    public static void getCount(ArrayList<String> arr){
        loop:for (int i = 0 ;i < arr.size(); i++) {
            //这段代码用于实现在比较之前输出的的语句是否有曾经输出过的元素值,
            //如果有则跳出loop循环,不执行下面的打印语句.
            if (i > 0) {
                for (int j = 0; j <i ; j++) {
                    if (Objects.equals(arr.get(i), arr.get(j))) {
                        continue loop;
                    }
                }
            }
            // 这段代码用于实现代码的判断元素值出现的次数并且打印出输出次数.
            int count = 0;
            for (String k : arr) {
                if (Objects.equals(arr.get(i), k)) {
                    count++;
                }
            }
            map.put(arr.get(i), count);
        }
    }
}




