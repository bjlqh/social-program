package com.tensquare.article.controller;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Comment comment) {
        commentService.add(comment);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    @RequestMapping("/{articleid}")
    public Result findByArticleid(@PathVariable String articleid) {
        List<Comment> comments = commentService.findByArticleid(articleid);
        return new Result(true, StatusCode.OK, "查询成功", comments);
    }

    @RequestMapping("/{id}")
    public Result deleteComment(@PathVariable String id) {
        commentService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
