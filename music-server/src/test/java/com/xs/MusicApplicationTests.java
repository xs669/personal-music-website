package com.xs;

import com.xs.domain.Ranks;
import com.xs.dto.ConsumerDto;
import com.xs.dto.SingerDto;
import com.xs.dto.SongDto;
import com.xs.mapper.*;
import com.xs.service.TestService;
import com.xs.vo.ListSongVo;
import com.xs.vo.SingerVo;
import com.xs.vo.SongListVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
public class MusicApplicationTests {

    @Resource
    private RanksMapper ranksMapper;

    @Resource
    private ConsumerMapper consumerMapper;

    @Resource
    private SingerMapper singerMapper;

    @Resource
    private SongListMapper songListMapper;

    @Resource
    private SongMapper songMapper;

    @Resource
    private ListSongMapper listSongMapper;

    @Resource
    private TestService testService;

    private static final Map<String, Integer> map = new HashMap<>();

    private static final Map<String, Integer> linkMap = new LinkedHashMap<>();

    @Test
    public void changeUrl() {
        testService.changeUrl();
    }

    /**
     * 按性别分组查询用户数量
     */
    @Test
    public void getConsumerCountBySex() {
        List<ConsumerDto> consumerCountBySex = consumerMapper.getConsumerCountBySex();
        System.out.println(consumerCountBySex);
    }

    /**
     * 按性别分组查询歌手数量
     */
    @Test
    public void getSingerCountBySex() {
        List<SingerDto> singerCountBySex = singerMapper.getSingerCountBySex();
        System.out.println(singerCountBySex);
    }

    @Test
    public void getAllListSongBySongListId() {
        Long id = 1579766027298848769L;
        List<ListSongVo> allListSongBySongListId = listSongMapper.getAllListSongBySongListId(id);
        System.out.println(allListSongBySongListId);
    }

    @Test
    public void getAllSongBySongListId() {
        Long id = 1579766027298848769L;
        List<SongDto> allSongBySongListId = songMapper.getAllSongBySongListId(id);
        System.out.println(allSongBySongListId);
    }

    /**
     * 按地区分组查询歌手数量
     */
    @Test
    public void getSingerCountByLocation() {
        List<SingerVo> singerCountByLocation = singerMapper.getSingerCountByLocation();
        System.out.println(singerCountByLocation);
    }

    /**
     * 按歌单类型分组查询的歌单数量
     */
    @Test
    public void getSongListCountByStyle() {
        List<SongListVo> songListCountByStyle = songListMapper.getSongListCountByStyle();
        System.out.println(songListCountByStyle);
    }

    @Test
    public void getRank() {
        List<Ranks> allRanks = ranksMapper.getAllRanks();
        System.out.println(allRanks);
    }

    @Test
    public void getScore() {
        double[] allScore = ranksMapper.getAllScore(1579766027298848769L);
        System.out.println(Arrays.toString(allScore));
    }

    @Test
    public void getAllSong() {
        List<SongDto> allSong = songMapper.getAllSong();
        System.out.println(allSong);
    }

    @Test
    public void getData() {
        System.out.println(LocalDateTime.now());
    }

    @Test
    public void getStyle() {
        ArrayList<String> arrayList = new ArrayList<>();
        String[] allStyle = songListMapper.getAllStyle();
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
            // 取出排名前5的值
            if (sort <= 7) {
                linkMap.put(newEntry.getKey(), newEntry.getValue());
                ++sort;
            }
        }
        System.out.println(linkMap);
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
