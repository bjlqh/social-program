package com.tensquare.spit.controller;

import com.tensquare.spit.exception.ThumbupException;
import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {
    @Autowired
    private SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询全部
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit) {
        spitService.add(spit);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Result update(@RequestBody Spit spit, @PathVariable String id) {
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Result deleteById(@PathVariable String id) {
        spitService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result search(@RequestBody Spit spit, @PathVariable Integer page, @PathVariable Integer size) {
        Page<Spit> spitPage = spitService.findConditions(spit, page, size);
        PageResult<Spit> pageResult = new PageResult<>(spitPage.getTotalElements(), spitPage.getContent());
        return new Result(true, StatusCode.OK, "条件查询成功", pageResult);
    }

    @RequestMapping(value = "/comment/{parentid}/{page}/{size}", method = RequestMethod.GET)
    public Result comment(@PathVariable String parentid, @PathVariable Integer page, @PathVariable Integer size) {
        Page<Spit> spitPage = spitService.findByParentid(parentid, page, size);
        PageResult<Spit> pageResult = new PageResult<>(spitPage.getTotalElements(), spitPage.getContent());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 点赞
     *
     * @param spitid
     * @return
     */
    @RequestMapping(value = "/thumbup/{spitid}", method = RequestMethod.PUT)
    public Result updateThumbup(@PathVariable String spitid) throws ThumbupException {
        //获取用户登录信息
        String userid = "1001";
        Object value = redisTemplate.opsForValue().get("userid_" + userid + "spitid_" + spitid);
        if (value != null) {
            throw new ThumbupException("您已经点赞过了");
        }
        spitService.updateThumbup(spitid);
        //记录用户点赞信息
        redisTemplate.opsForValue().set("userid_" + userid + "spitid_" + spitid, "1");
        return new Result(true, StatusCode.OK, "点赞成功");
    }

}
