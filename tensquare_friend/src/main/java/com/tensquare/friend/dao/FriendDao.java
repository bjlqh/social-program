package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend, String> {
    /**
     * 根据用户id和好友id-----查询用户的交友记录
     *
     * @param userid
     * @param friendid
     * @return
     */
    Friend findByUseridAndFriendid(String userid, String friendid);

    /**
     * 更新islike
     *
     * @param userid
     * @param friendid
     * @param islike
     */
    @Modifying
    @Query("update Friend set islike =?3 where userid=?1 and friendid=?2")
    void updateIslike(String userid, String friendid, String islike);

    @Modifying
    @Query("delete from Friend f where f.userid=?1 and f.friendid=?2")
    void deleteFriend(String userid, String friendid);
}
