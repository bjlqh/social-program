package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleSearchDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import util.IdWorker;

@Service
public class ArticleSearchService {
    @Autowired
    private ArticleSearchDao articleSearchDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 添加文章到elasticsearch
     *
     * @param article
     */
    public void add(Article article) {
        article.setId(String.valueOf(idWorker.nextId()));
        articleSearchDao.save(article);
    }

    /**
     * 查询所有
     *
     * @return
     */
    public Iterable<Article> findAll() {
        return articleSearchDao.findAll();
    }

    /**
     * 自定义关键字查询
     *
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findKeyword(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return articleSearchDao.findByTitleLikeOrContentLike(keyword, keyword, pageable);
    }
}
