package com.tensquare.base.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tb_label")
public class Label implements Serializable {
    @Id
    private String id;//主键
    private String labelname;//标签名称
    private String state;//状态 1:可用  0:不可用
    private Long count;//使用数量
    private Long fans;//关注数
    private String recommend;//是否推荐 1:推荐  0:不推荐

}
