package com.tensquare.spit.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Data
public class Spit implements Serializable {
    @Id
    private String _id;//mongodb的唯一标识
    private String content;//吐槽内容
    private Date publishtime;//发布日期
    private String userid;//发布人id
    private String nickname;//发布人昵称
    private Integer visits;//访问数
    private Integer thumbup;//点赞数
    private Integer comment;//回复数
    private String state;//是否可见 1:可见 0:不可见
    private Integer share;//分享数
    private String parentid;//上一级id
}
