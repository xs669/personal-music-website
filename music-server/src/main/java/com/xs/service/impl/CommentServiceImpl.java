package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.Result;
import com.xs.domain.Comment;
import com.xs.domain.Consumer;
import com.xs.dto.CommentDto;
import com.xs.mapper.ConsumerMapper;
import com.xs.service.CommentService;
import com.xs.mapper.CommentMapper;
import com.xs.vo.CommentVo;
import com.xs.vo.SearchVo;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Resource
    private ConsumerMapper consumerMapper;

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
        if (Objects.nonNull(allComment) && !allComment.isEmpty()) {
            return Result.ok("查询成功", allComment);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 查询指定歌单所有评论
     */
    @Override
    public Result getAllCommentBySongListId(Long id) {
        List<CommentVo> commentVoList = new ArrayList<>();
        List<Comment> allCommentBySongListId = commentMapper.getAllCommentBySongListId(id);
        return getResult(commentVoList, allCommentBySongListId);
    }

    @NotNull
    private Result getResult(List<CommentVo> commentVoList, List<Comment> allCommentBySongListId) {
        if (Objects.nonNull(allCommentBySongListId) && !allCommentBySongListId.isEmpty()) {
            for (Comment comment : allCommentBySongListId) {
                Consumer consumer = consumerMapper.selectById(comment.getUserId());
                if (Objects.nonNull(consumer)) {
                    CommentVo commentVo = CommentVo.builder()
                            .id(comment.getId())
                            .username(consumer.getUsername())
                            .avatar(consumer.getAvatar())
                            .content(comment.getContent())
                            .createTime(comment.getCreateTime())
                            .up(comment.getUp()).build();
                    commentVoList.add(commentVo);
                } else {
                    return Result.error("数据为空");
                }
            }
            return Result.ok("查询成功", commentVoList);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 查询指定歌曲所有评论
     */
    @Override
    public Result getAllCommentBySongId(Long id) {
        List<CommentVo> commentVoList = new ArrayList<>();
        List<Comment> allCommentBySongId = commentMapper.getAllCommentBySongId(id);
        return getResult(commentVoList, allCommentBySongId);
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
        List<CommentVo> commentVoList = new ArrayList<>();
        LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Comment::getUserId, id);
        List<Comment> commentList = commentMapper.selectList(lqw);
        Consumer consumer = consumerMapper.selectById(id);
        if (Objects.nonNull(commentList) && !commentList.isEmpty()) {
            for (Comment comment : commentList) {
                CommentVo commentVo = CommentVo.builder()
                        .id(comment.getId())
                        .username(consumer.getUsername())
                        .avatar(consumer.getAvatar())
                        .content(comment.getContent())
                        .createTime(comment.getCreateTime())
                        .up(comment.getUp()).build();
                commentVoList.add(commentVo);
            }
            return Result.ok("查询成功", commentVoList);
        } else {
            return Result.error("查询失败");
        }
    }

    /**
     * 按评论内容或用户名模糊搜索
     */
    @Override
    public Result searchComment(SearchVo searchVo) {
        if (Objects.nonNull(searchVo) && Objects.nonNull(searchVo.getKeyWord())) {
            List<CommentVo> commentVoList = new ArrayList<>();
            Integer type = searchVo.getType();
            if (type == 0) {
                LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<>();
                lqw.eq(Comment::getUserId, searchVo.getId());
                List<Comment> commentList = commentMapper.selectList(lqw);
                if (Objects.nonNull(commentList) && !commentList.isEmpty()) {
                    for (Comment comment : commentList) {
                        Consumer consumer = consumerMapper.selectById(comment.getUserId());
                        if (comment.getContent().contains(searchVo.getKeyWord()) || consumer.getUsername().contains(searchVo.getKeyWord())) {
                            CommentVo commentVo = CommentVo.builder().id(comment.getId())
                                    .username(consumer.getUsername())
                                    .avatar(consumer.getAvatar())
                                    .content(comment.getContent())
                                    .createTime(comment.getCreateTime())
                                    .up(comment.getUp()).build();
                            commentVoList.add(commentVo);
                        }
                    }
                    return Result.ok("查询成功", commentVoList);
                } else {
                    return Result.error("查询失败");
                }
            }
            if (type == 1) {
                LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<>();
                lqw.eq(Comment::getSongId, searchVo.getId());
                List<Comment> commentList = commentMapper.selectList(lqw);
                if (Objects.nonNull(commentList) && !commentList.isEmpty()) {
                    for (Comment comment : commentList) {
                        Consumer consumer = consumerMapper.selectById(comment.getUserId());
                        if (comment.getContent().contains(searchVo.getKeyWord()) || consumer.getUsername().contains(searchVo.getKeyWord())) {
                            CommentVo commentVo = CommentVo.builder().id(comment.getId())
                                    .username(consumer.getUsername())
                                    .avatar(consumer.getAvatar())
                                    .content(comment.getContent())
                                    .createTime(comment.getCreateTime())
                                    .up(comment.getUp()).build();
                            commentVoList.add(commentVo);
                        }
                    }
                    return Result.ok("查询成功", commentVoList);
                } else {
                    return Result.error("查询失败");
                }
            }
            if (type == 2) {
                LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<>();
                lqw.eq(Comment::getSongListId, searchVo.getId());
                List<Comment> commentList = commentMapper.selectList(lqw);
                if (Objects.nonNull(commentList) && !commentList.isEmpty()) {
                    for (Comment comment : commentList) {
                        Consumer consumer = consumerMapper.selectById(comment.getUserId());
                        if (comment.getContent().contains(searchVo.getKeyWord()) || consumer.getUsername().contains(searchVo.getKeyWord())) {
                            CommentVo commentVo = CommentVo.builder().id(comment.getId())
                                    .username(consumer.getUsername())
                                    .avatar(consumer.getAvatar())
                                    .content(comment.getContent())
                                    .createTime(comment.getCreateTime())
                                    .up(comment.getUp()).build();
                            commentVoList.add(commentVo);
                        }
                    }
                    return Result.ok("查询成功", commentVoList);
                } else {
                    return Result.error("查询失败");
                }
            }
        } else {
            return Result.error("查询失败");
        }
        return Result.error("查询失败");
    }
}




