package com.xs.controller;

import com.xs.common.Result;
import com.xs.domain.Comment;
import com.xs.dto.CommentDto;
import com.xs.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 添加评论
     */
    @PostMapping("/add")
    public Result addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    /**
     * 修改评论
     */
    @PutMapping("/update")
    public Result updateComment(@RequestBody Comment comment) {
        return commentService.updateComment(comment);
    }

    /**
     * 删除评论
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }

    /**
     * 查询所有评论
     */
    @GetMapping("/all")
    public Result getAllComment() {
        return commentService.getAllComment();
    }

    /**
     * 查询指定歌单所有评论
     */
    @GetMapping("/commentOfSongListId/{id}")
    public Result getAllCommentBySongListId(@PathVariable Long id) {
        return commentService.getAllCommentBySongListId(id);
    }

    /**
     * 查询指定歌曲所有评论
     */
    @GetMapping("/commentOfSongId/{id}")
    public Result getAllCommentBySongId(@PathVariable Long id) {
        return commentService.getAllCommentBySongId(id);
    }

    /**
     * 给指定评论点赞
     */
    @PutMapping("/updateCommentUp")
    public Result updateCommentUp(@RequestBody CommentDto commentDto) {
        return commentService.updateCommentUp(commentDto);
    }

    /**
     * 取消点赞
     */
    @PutMapping("/cancelCommentUp")
    public Result cancelCommentUp(@RequestBody CommentDto commentDto) {
        return commentService.cancelCommentUp(commentDto);
    }
}
