package com.tensquare.user.controller;

import com.tensquare.user.exception.RepetitionRegisterException;
import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private HttpServletRequest request;

    /**
     * 增加关注数
     *
     * @param userid
     * @param follow
     * @return
     */
    @RequestMapping(value = "/incfollow/{userid}/{follow}", method = RequestMethod.POST)
    public Result updateFollowCount(@PathVariable String userid, @PathVariable int follow) {
        userService.updateFollowCount(userid, follow);
        return new Result(true, StatusCode.OK, "关注成功");
    }

    /**
     * 增加粉丝数
     *
     * @param friendid
     * @param fans
     * @return
     */
    @RequestMapping(value = "/incfans/{friendid}/{fans}", method = RequestMethod.POST)
    public Result updateFansCount(@PathVariable String friendid, @PathVariable int fans) {
        userService.updateFansCount(friendid, fans);
        return new Result(true, StatusCode.OK, "加粉成功");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, String> map) {
        User user = userService.login(map.get("mobile"), map.get("password"));
        if (user != null) {
            //生成token
            String token = jwtUtil.createJWT(user.getId(), user.getNickname(), "ROLE_USER");
            Map<String, Object> jwtmap = new HashMap<>();
            jwtmap.put("token", token);
            jwtmap.put("name", user.getNickname());
            jwtmap.put("avatar", user.getAvatar());
            return new Result(true, StatusCode.OK, "登录成功", jwtmap);

        } else {
            return new Result(false, StatusCode.LOGINERROR, "用户名或密码错误");
        }
    }

    /**
     * 发送短信
     *
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/sendsms/{mobile}", method = RequestMethod.POST)
    public Result sendSms(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK, "短信发送成功");
    }

    /**
     * 注册用户信息
     *
     * @param user
     * @param code
     * @return
     * @throws RepetitionRegisterException
     */
    @RequestMapping(value = "/register/{code}", method = RequestMethod.POST)
    public Result register(@RequestBody User user, @PathVariable String code) throws RepetitionRegisterException {
        userService.register(user, code);
        return new Result(true, StatusCode.OK, "注册成功");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param user
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody User user) {
        userService.add(user);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        Claims adminClaims = (Claims) request.getAttribute("admin_claims");
        if (adminClaims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "无权访问");
        }
        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /*@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id, @RequestHeader(name = "Authorization", required = false) String header) {
        //1.判断是否有消息头
        if (StringUtils.isEmpty(header)) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        //2.判断消息头的格式是否正确
        if (header.startsWith("Bearer ")) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足,消息头格式不正确");
        }
        //3.获取token解析 Bearer token
        String token = header.split(" ")[1];
        Claims claims = null;
        try {
            claims = jwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ACCESSERROR, "权限不足,token解析错误");
        }
        //4.判断声明是否为空
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足,有效信息不存在");
        }
        //5.判断权限是否正确
        if (!"admin_role".equals(claims.get("roles"))) {
            return new Result(false, StatusCode.ACCESSERROR, "权限错误");
        }
        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }*/
}
