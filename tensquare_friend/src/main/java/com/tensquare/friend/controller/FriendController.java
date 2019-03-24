package com.tensquare.friend.controller;

import com.tensquare.friend.service.BlacklistService;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private BlacklistService blacklistService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/like/{friendid}/{type}", method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid, @PathVariable String type) {
        //1.获取token声明
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "无权访问");
        }
        String userid = claims.getId();
        //2.加好友或拉黑
        if ("1".equals(type)) {
            //加为好友
            friendService.addFriend(userid, friendid);
            return new Result(true, StatusCode.OK, "添加好友成功");
        } else {
            //拉黑
            blacklistService.addBlacklist(userid, friendid);
            return new Result(true, StatusCode.OK, "拉黑成功");
        }
    }

    @RequestMapping(value = "/{friendid}", method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable String friendid) {
        //1.获取token声明
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "无权访问");
        }
        String userid = claims.getId();
        friendService.deleteFriend(userid, friendid);
        return new Result(true, StatusCode.OK, "删除好友成功");
    }
}
