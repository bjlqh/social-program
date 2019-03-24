package com.tensquare.recruitt.dao;

import com.tensquare.recruitt.pojo.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface EnterpriseDao extends JpaRepository<Enterprise, String>, JpaSpecificationExecutor<Enterprise> {
    /**
     * 根据热门状态查询企业列表
     *
     * @param ishot
     * @return
     */
    List<Enterprise> findTop12ByIshot(String ishot);
}
