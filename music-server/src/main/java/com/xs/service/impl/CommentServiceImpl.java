package com.xs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.Result;
import com.xs.domain.Comment;
import com.xs.dto.CommentDto;
import com.xs.mapper.CommentMapper;
import com.xs.service.CommentService;
import com.xs.vo.CommentVo;
import com.xs.vo.SearchVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author xs
 * description 针对表【comment(评论)】的数据库操作Service实现
 * createDate 2022-10-11 16:10:41
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService{

    @Resource
    private CommentMapper commentMapper;

    /**
     * 添加评论
     */
    @Override
    public Result addComment(Comment comment) {
        int i = commentMapper.insert(comment);
        if (i > 0) {
            return Result.ok("评论成功");
        } else {
            return Result.ok("评论失败");
        }
    }

    /**
     * 修改评论
     */
    @Override
    public Result updateComment(Comment comment) {
        int i = commentMapper.updateById(comment);
        if (i > 0) {
            return Result.ok("修改成功");
        } else {
            return Result.ok("修改失败");
        }
    }

    /**
     * 删除评论
     */
    @Override
    public Result deleteComment(Long id) {
        int i = commentMapper.deleteById(id);
        if (i > 0) {
            return Result.ok("删除成功");
        } else {
            return Result.ok("删除失败");
        }
    }

    /**
     * 查询所有评论
     */
    @Override
    public Result getAllComment() {
        List<Comment> allComment = commentMapper.getAllComment();
        return Result.ok("查询成功", allComment);
    }

    /**
     * 查询指定歌单所有评论
     */
    @Override
    public Result getAllCommentBySongListId(Long id) {
        List<CommentVo> allCommentBySongListId = commentMapper.getAllCommentBySongListId(id);
        return Result.ok("查询成功", allCommentBySongListId);
    }

    /**
     * 查询指定歌曲所有评论
     */
    @Override
    public Result getAllCommentBySongId(Long id) {
        List<CommentVo> allCommentBySongId = commentMapper.getAllCommentBySongId(id);
        return Result.ok("查询成功", allCommentBySongId);
    }

    /**
     * 给指定评论点赞
     */
    @Override
    public Result updateCommentUp(CommentDto commentDto) {
        Comment comment = commentMapper.selectById(commentDto.getId());
        if (Objects.nonNull(comment)) {
            comment.setUp(comment.getUp() + 1);
            comment.setCreateTime(LocalDateTime.now());
            int i = commentMapper.updateById(comment);
            if (i > 0) {
                return Result.ok("点赞成功");
            } else {
                return Result.ok("点赞失败");
            }
        } else {
            return Result.error("点赞失败");
        }
    }

    /**
     * 取消点赞
     */
    @Override
    public Result cancelCommentUp(CommentDto commentDto) {
        Comment comment = commentMapper.selectById(commentDto.getId());
        if (Objects.nonNull(comment)) {
            comment.setUp(comment.getUp() - 1);
            comment.setCreateTime(LocalDateTime.now());
            int i = commentMapper.updateById(comment);
            if (i > 0) {
                return Result.ok("取消点赞成功");
            } else {
                return Result.ok("取消点赞失败");
            }
        } else {
            return Result.error("取消点赞失败");
        }
    }

    /**
     * 批量删除评论
     */
    @Override
    public Result deleteAllComment(Long[] ids) {
        if (Objects.nonNull(ids)) {
            for (Long id : ids) {
                commentMapper.deleteById(id);
            }
            return Result.ok("批量删除成功");
        } else {
            return Result.error("批量删除失败");
        }
    }

    /**
     * 获取指定用户所有评论
     */
    @Override
    public Result getAllCommentByUserId(Long id) {
        List<CommentVo> allCommentByUserId = commentMapper.getAllCommentByUserId(id);
        return Result.ok("查询成功", allCommentByUserId);
    }

    /**
     * 按评论内容或用户名模糊搜索
     */
    @Override
    public Result searchComment(SearchVo searchVo) {
        if (Objects.nonNull(searchVo) && Objects.nonNull(searchVo.getKeyWord())) {
            Integer type = searchVo.getType();
            if (type == 0) {
                List<CommentVo> commentVoList = commentMapper.searchCommentByUserId(searchVo.getId(), searchVo.getKeyWord());
                return Result.ok("查询成功", commentVoList);
            }
            if (type == 1) {
                List<CommentVo> commentVoList = commentMapper.searchCommentBySongId(searchVo.getId(), searchVo.getKeyWord());
                return Result.ok("查询成功", commentVoList);
            }
            if (type == 2) {
                List<CommentVo> commentVoList = commentMapper.searchCommentBySongListId(searchVo.getId(), searchVo.getKeyWord());
                return Result.ok("查询成功", commentVoList);
            }
        } else {
            return Result.error("查询失败");
        }
        return Result.error("查询失败");
    }
}




