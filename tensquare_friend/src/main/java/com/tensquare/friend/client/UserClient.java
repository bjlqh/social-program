package com.tensquare.friend.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 调用用户微服务
 */
@FeignClient("tensquare-user")
public interface UserClient {

    /**
     * 添加关注数
     *
     * @param userid
     * @param follow
     * @return
     */
    @RequestMapping(value = "/user/incfollow/{userid}/{follow}", method = RequestMethod.POST)
    Result updateFollowCount(@PathVariable("userid") String userid, @PathVariable("follow") int follow);

    /**
     * 增加粉丝数
     *
     * @param friendid
     * @param fans
     * @return
     */
    @RequestMapping(value = "/user/incfans/{friendid}/{fans}", method = RequestMethod.POST)
    Result updateFansCount(@PathVariable("friendid") String friendid, @PathVariable("fans") int fans);
}
