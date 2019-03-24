package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleSearchService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleSearchController {
    @Autowired
    private ArticleSearchService articleSearchService;

    /**
     * 添加文章
     *
     * @param article
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article) {
        articleSearchService.add(article);
        return new Result(true, StatusCode.OK, "文章添加成功");
    }

    /**
     * 全部查询
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        Iterable<Article> iterable = articleSearchService.findAll();
        return new Result(true, StatusCode.OK, "查询全部成功", iterable);
    }

    /**
     * 根据关键字搜索
     *
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{keywords}/{page}/{size}", method = RequestMethod.GET)
    public Result findKeyword(@PathVariable String keywords, @PathVariable int page, @PathVariable int size) {
        Page<Article> articlePage = articleSearchService.findKeyword(keywords, page, size);
        PageResult<Article> pageResult = new PageResult<>();
        pageResult.setRows(articlePage.getContent());
        pageResult.setTotal(articlePage.getTotalElements());
        return new Result(true, StatusCode.OK, "关键字搜索成功", pageResult);
    }
}
