package com.tensquare.recruitt.dao;

import com.tensquare.recruitt.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface RecruitDao extends JpaRepository<Recruit, String>, JpaSpecificationExecutor<Recruit> {
    /**
     * 查询最新推荐职位列表(按创建日期降序排序)
     */
    List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state);

    /**
     * 查询最新职位列表，状态不为0并以创建日期降序排序，查询前12条记录
     */
    List<Recruit> findTop12ByStateNotOrderByCreatetimeDesc(String state);
}
