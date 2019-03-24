package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistDao extends JpaRepository<NoFriend, String> {
    /**
     * 查询我的黑名单用户
     *
     * @param userid
     * @param friendid
     * @return
     */
    NoFriend findByUseridAndFriendid(String userid, String friendid);
}
