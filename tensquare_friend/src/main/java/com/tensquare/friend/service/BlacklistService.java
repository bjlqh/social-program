package com.tensquare.friend.service;

import com.tensquare.friend.dao.BlacklistDao;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlacklistService {
    @Autowired
    private BlacklistDao blacklistDao;
    @Autowired
    private FriendDao friendDao;

    public void addBlacklist(String userid, String friendid) {
        //1.判断对方是否将自己拉黑
        NoFriend noFriend = blacklistDao.findByUseridAndFriendid(userid, friendid);
        if (noFriend != null) {
            throw new IllegalStateException("对方已将您拉黑，无需再拉黑");
        }
        //2.判断是否为好友关系
        Friend friend = friendDao.findByUseridAndFriendid(userid, friendid);
        if (friend != null) {
            throw new IllegalStateException("对方是您的好友，请先解除好友关系");
        }
        //3.加入拉黑列表
        noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        blacklistDao.save(noFriend);
    }
}
