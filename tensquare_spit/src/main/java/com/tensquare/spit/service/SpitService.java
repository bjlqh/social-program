package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
public class SpitService {
    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询所有记录
     *
     * @return
     */
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    /**
     * 对吐槽进行评论
     *
     * @param spit
     */
    public void add(Spit spit) {
        spit.set_id(String.valueOf(idWorker.nextId()));
        spit.setPublishtime(new Date());
        spit.setVisits(0);
        spit.setThumbup(0);
        spit.setShare(0);
        spit.setComment(0);
        spit.setState("1");
        //如果存在上级id,就是评论,对吐槽的回复数+1
        if (!StringUtils.isEmpty(spit.getParentid())) {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }
        spitDao.save(spit);
    }

    /**
     * 修改
     *
     * @param spit
     */
    public void update(Spit spit) {
        spitDao.save(spit);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    /**
     * 根据上级id查询吐槽列表
     *
     * @param parentid
     * @return
     */
    public Page<Spit> findByParentid(String parentid, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Spit> spitPage = spitDao.findByParentid(parentid, pageable);
        return spitPage;
    }

    /**
     * 根据内容content条件分页查询
     *
     * @param spit
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findConditions(Spit spit, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return spitDao.findByContentLike(spit.getContent(), pageable);
    }

    /**
     * 吐槽点赞
     * 使用mongodbTemplate
     *
     * @param spitid
     */
    /*public void updateThumbup(String spitid) {
        Spit spit = this.findById(spitid);
        spit.setThumbup(spit.getThumbup() + 1);
        spitDao.save(spit);
        执行效率不高，因为会把所有的字段全部查出来。
    }*/
    public void updateThumbup(String spitid) {
        //创建查询对象
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitid));
        //创建更新对象
        Update update = new Update();
        update.inc("thumbup", 1);
        //执行更新操作
        mongoTemplate.updateFirst(query, update, Spit.class);
    }

}
