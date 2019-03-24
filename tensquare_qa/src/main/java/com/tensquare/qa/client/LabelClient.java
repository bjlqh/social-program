package com.tensquare.qa.client;

import com.tensquare.qa.client.impl.LabelClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 需要调用的服务名称
 */
@FeignClient(value = "tensquare-base", fallback = LabelClientImpl.class)
public interface LabelClient {
    /**
     * 被调用的微服务的地址映射
     * 根据labelid 查询对象
     */
    @RequestMapping(value = "/label/{id}", method = RequestMethod.GET)
    Result findById(@PathVariable(value = "id") String id);
}
