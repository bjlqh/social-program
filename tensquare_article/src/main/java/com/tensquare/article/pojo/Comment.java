package com.tensquare.article.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Data
public class Comment implements Serializable {
    @Id
    private String _id; //主键
    private String articleid; //文章id
    private String content;//文章内容
    private String userid;//用户id
    private String parentid;//父id
    private Date publishdate;//发布时间
}
