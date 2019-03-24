package com.tensquare.user.dao;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    /**
     * 根据用户手机号查询用户信息
     *
     * @param mobile
     * @return
     */
    User findByMobile(String mobile);

    /**
     * 更新对方粉丝数
     *
     * @param friendid
     * @param fans
     */
    @Modifying
    @Query("update User u set u.fanscount=fanscount+?2 where u.id=?1")
    void updateFansCount(String friendid, int fans);

    /**
     * 更新自己的关注数
     *
     * @param userid
     * @param follow
     */
    @Modifying
    @Query("update User u set u.followcount=followcount+?2 where u.id=?1")
    void updateFollowCount(String userid, int follow);


}
