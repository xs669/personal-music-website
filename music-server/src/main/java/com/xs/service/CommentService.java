package com.xs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xs.common.Result;
import com.xs.domain.Comment;
import com.xs.dto.CommentDto;
import com.xs.vo.SearchVo;

/**
 * @author xs
 * description 针对表【comment(评论)】的数据库操作Service
 * createDate 2022-10-11 16:10:41
*/
public interface CommentService extends IService<Comment> {

    /**
     * 添加评论
     */
    Result addComment(Comment comment);

    /**
     * 修改评论
     */
    Result updateComment(Comment comment);

    /**
     * 删除评论
     */
    Result deleteComment(Long id);

    /**
     * 查询所有评论
     */
    Result getAllComment();

    /**
     * 查询指定歌单所有评论
     */
    Result getAllCommentBySongListId(Long id);

    /**
     * 查询指定歌曲所有评论
     */
    Result getAllCommentBySongId(Long id);

    /**
     * 给指定评论点赞
     */
    Result updateCommentUp(CommentDto commentDto);

    /**
     * 取消点赞
     */
    Result cancelCommentUp(CommentDto commentDto);

    /**
     * 批量删除评论
     */
    Result deleteAllComment(Long[] ids);

    /**
     * 获取指定用户所有评论
     */
    Result getAllCommentByUserId(Long id);

    /**
     * 按评论内容或用户名模糊搜索
     */
    Result searchComment(SearchVo searchVo);
}
