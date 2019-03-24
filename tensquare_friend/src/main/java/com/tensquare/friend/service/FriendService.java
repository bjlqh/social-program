package com.tensquare.friend.service;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.dao.BlacklistDao;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendService {
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private BlacklistDao blacklistDao;
    @Autowired
    private UserClient userClient;

    @Transactional
    public void addFriend(String userid, String friendid) {
        //1.判断是否加对方为好友
        Friend friend = friendDao.findByUseridAndFriendid(userid, friendid);
        if (friend != null) {
            throw new IllegalStateException("已添加对方为好友,无需重复添加");
        }
        //2.判断是否把对方拉黑
        NoFriend noFriend = blacklistDao.findByUseridAndFriendid(userid, friendid);
        if (noFriend != null) {
            throw new IllegalStateException("对方在您的黑名单当中，不能再加为好友");
        }
        //3.判断对方是否把自己拉黑
        noFriend = blacklistDao.findByUseridAndFriendid(friendid, userid);
        if (noFriend != null) {
            throw new IllegalStateException("您在对方的黑名单中，不能加对方为好友");
        }
        //4.添加对方为好友
        friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        //5.判断对方是否加自己为好友
        Friend inverseFriend = friendDao.findByUseridAndFriendid(friendid, userid);
        if (inverseFriend != null) {
            //对方已添加自己为好友，更新islike为1
            friendDao.updateIslike(userid, friendid, "1");
            friendDao.updateIslike(friendid, userid, "1");
        }
        //6.更新自己的关注数
        userClient.updateFollowCount(userid, 1);
        //7.更新对方的粉丝数
        userClient.updateFansCount(friendid, 1);
    }

    @Transactional
    public void deleteFriend(String userid, String friendid) {
        friendDao.deleteFriend(userid, friendid);
        //1.判断是否为双向喜欢
        Friend friend = friendDao.findByUseridAndFriendid(friendid, userid);
        if (friend != null) {
            //是双向喜欢，更新对方字段islike为0
            friendDao.updateIslike(friendid, userid, "0");
        }
        //2.减少自己的关注数
        userClient.updateFollowCount(userid, -1);
        //3.减少对方的粉丝数
        userClient.updateFansCount(friendid, -1);
    }
}