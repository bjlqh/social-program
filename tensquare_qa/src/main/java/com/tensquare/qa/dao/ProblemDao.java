package com.tensquare.qa.dao;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {
    /**
     * 根据标签ID查询最新问题列表
     *
     * @return
     */
    @Query("SELECT pr FROM Problem pr WHERE pr.id IN (SELECT problemid FROM PL WHERE labelid=?1) AND replytime IS NOT NULL ORDER BY replytime DESC")
    Page<Problem> findNewListByLabelId(String labelId, Pageable pageable);

    /**
     * 按回复数降序排序
     *
     * @param labelid
     * @param pageable
     * @return
     */
    @Query("SELECT p FROM Problem p WHERE p.id IN(SELECT problemid FROM PL WHERE labelid=?1) AND reply IS NOT NULL ORDER BY reply DESC")
    Page<Problem> findHotList(String labelid, Pageable pageable);

    /**
     * 根据标签ID查询等待回答列表
     *
     * @param labelid
     * @param pageable
     * @return
     */
    @Query("SELECT p FROM Problem p WHERE p.id IN(SELECT problemid FROM PL WHERE labelid=?1) AND reply ='0'ORDER BY createtime DESC")
    Page<Problem> findWaitList(String labelid, Pageable pageable);
}
