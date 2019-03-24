package com.tensquare.friend.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_friend")
@IdClass(Friend.class)//当前类是一个id类
@Data
public class Friend implements Serializable {
    @Id
    private String userid;//用户id
    @Id
    private String friendid;//好友的用户id
    private String islike;//是否相互关注  0：单向  1：双向
}
