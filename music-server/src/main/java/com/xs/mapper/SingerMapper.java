package com.xs.mapper;

import com.xs.domain.Singer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xs.dto.SingerDto;
import com.xs.vo.SingerVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xs
 * description 针对表【singer(歌手)】的数据库操作Mapper
 * createDate 2022-10-05 15:08:13
 * Entity com.xs.domain.Singer
*/
@Repository
@Mapper
public interface SingerMapper extends BaseMapper<Singer> {

    /**
     * 查询所有歌手
     */
    List<Singer> selectAllSinger();

    /**
     * 按歌手名称查询
     */
    List<Singer> getSingerByName(String name);

    /**
     * 获取歌手总数
     */
    int getSingerCount();

    /**
     * 按歌手id查询歌手名
     */
    String getSingerNameById(Long id);

    /**
     * 按性别分组查询歌手数量
     */
    List<SingerDto> getSingerCountBySex();

    /**
     * 按地区分组查询歌手数量
     */
    List<SingerVo> getSingerCountByLocation();

    /**
     * 按性别查询歌手数量
     */
    Integer getAllSingerCountBySex(Integer sex);
}




