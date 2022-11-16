package com.xs.mapper;

import com.xs.domain.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xs.vo.CommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xs
 * description 针对表【comment(评论)】的数据库操作Mapper
 * createDate 2022-10-11 16:10:41
 * Entity com.xs.domain.Comment
*/
@Repository
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 按用户id删除评论信息
     */
    int deleteByUserId(Long id);

    /**
     * 按歌单id删除评论信息
     */
    int deleteBySongListId(Long id);

    /**
     * 按歌曲id删除评论信息
     */
    int deleteBySongId(Long id);

    /**
     * 查询所有评论
     */
    List<Comment> getAllComment();

    /**
     * 查询指定歌单所有评论
     */
    List<CommentVo> getAllCommentBySongListId(Long id);

    /**
     * 查询指定歌曲所有评论
     */
    List<CommentVo> getAllCommentBySongId(Long id);

    /**
     * 查询指定用户所有评论
     */
    List<CommentVo> getAllCommentByUserId(Long id);

    /**
     * 模糊查询指定用户评论
     */
    List<CommentVo> searchCommentByUserId(Long id, String keyWord);

    /**
     * 模糊查询指定歌曲评论
     */
    List<CommentVo> searchCommentBySongId(Long id, String keyWord);

    /**
     * 模糊查询指定歌单评论
     */
    List<CommentVo> searchCommentBySongListId(Long id, String keyWord);
}




