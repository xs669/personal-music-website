package com.xs.controller.admin;

import com.xs.common.Result;
import com.xs.domain.Comment;
import com.xs.dto.CommentDto;
import com.xs.service.CommentService;
import com.xs.vo.SearchVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/comment")
public class CommentAdminController {

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
     * 批量删除评论
     */
    @DeleteMapping("/deleteAll/{ids}")
    public Result deleteAllComment(@PathVariable Long[] ids) {
        return commentService.deleteAllComment(ids);
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

    /**
     * 获取指定用户所有评论
     */
    @GetMapping("/commentOfUserId/{id}")
    public Result getAllCommentByUserId(@PathVariable Long id) {
        return commentService.getAllCommentByUserId(id);
    }

    /**
     * 按评论内容或用户名模糊搜索
     */
    @PostMapping("/searchComment")
    public Result searchComment(@RequestBody SearchVo searchVo) {
        return commentService.searchComment(searchVo);
    }
}
