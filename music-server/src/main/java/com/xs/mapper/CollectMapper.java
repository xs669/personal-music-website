package com.xs.mapper;

import com.xs.domain.Collect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xs
 * description 针对表【collect(收藏)】的数据库操作Mapper
 * createDate 2022-10-11 16:14:36
 * Entity com.xs.domain.Collect
*/
@Repository
@Mapper
public interface CollectMapper extends BaseMapper<Collect> {

    /**
     * 按歌单id删除收藏信息
     */
    int deleteBySongListId(Long id);

    /**
     * 按歌曲id删除收藏信息
     */
    int deleteBySongId(Long id);

    /**
     * 按用户id删除收藏信息
     */
    int deleteByUserId(Long id);

    /**
     * 获取指定用户所有收藏歌曲
     */
    List<Collect> getAllCollectByConsumerId(Long id);

}




