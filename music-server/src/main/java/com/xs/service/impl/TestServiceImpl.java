package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xs.domain.Singer;
import com.xs.domain.Song;
import com.xs.domain.SongList;
import com.xs.mapper.SingerMapper;
import com.xs.mapper.SongListMapper;
import com.xs.mapper.SongMapper;
import com.xs.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Resource
    private SingerMapper singerMapper;

    @Resource
    private SongMapper songMapper;

    @Resource
    private SongListMapper songListMapper;

    @Override
    public void changeUrl() {
        List<SongList> allSongList = songListMapper.getAllSongList();
        for (SongList songList : allSongList) {
            String pic = songList.getPic();
            String replace = pic.replace("http://localhost/", "https://www.freemusic.ltd/");
            songList.setPic(replace);
            songListMapper.updateById(songList);
        }
        List<Singer> singerList = singerMapper.selectAllSinger();
        for (Singer singer : singerList) {
            String pic = singer.getPic();
            String replace = pic.replace("http://localhost/", "https://www.freemusic.ltd/");
            singer.setPic(replace);
            singerMapper.updateById(singer);
        }
        LambdaQueryWrapper<Song> lqw = new LambdaQueryWrapper<>();
        List<Song> songList = songMapper.selectList(lqw);
        for (Song song : songList) {
            String pic = song.getPic();
            String replace = pic.replace("http://localhost/", "https://www.freemusic.ltd/");
            song.setPic(replace);
            String url = song.getUrl();
            String replace1 = url.replace("http://localhost/", "https://www.freemusic.ltd/");
            song.setUrl(replace1);
            songMapper.updateById(song);
        }
    }
}
