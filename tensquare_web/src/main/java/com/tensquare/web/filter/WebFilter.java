package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，
 * 具体如下：自定义过滤器的实现，需要继承ZuulFilter，需要重写实现下面四个方法
 */
@Component
public class WebFilter extends ZuulFilter {
    /**
     * 指定当前过滤器的执行时机
     * pre: 可以在请求被路由之前调用
     * route: 在路由请求时被调用
     * error: 处理请求发生错误时调用
     * post: 在routing和error过滤器之后被调用
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }


    /**
     * 指定过滤器的执行顺序，当有多个网关过滤器时，用于确定执行顺序的。
     * 数值越小，执行时间点越早。
     * 取值是0 和 正整数
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }


    /**
     * 指定当前过滤器是否执行,我们可以加条件判定是否执行
     * 取值为true的时候表示执行。
     * 为false的时候不执行
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 用户在发布文章需要权限。
     * 用户查询文章是不需要权限。
     * 指定核心业务代码
     * 我们要在这里实现消息头的获取和转发
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //1.获取请求上下文----不要直接new，requestContext做了线程绑定，当前请求是当前用户的，保证每个用户的请求取不乱。
        RequestContext requestContext = RequestContext.getCurrentContext();
        //2.获取request
        HttpServletRequest request = requestContext.getRequest();
        //3.获取请求消息头
        String header = request.getHeader("Authorization");

        /*4.获取请求消息头进行验证----已在客户端使用拦截器拦截
         *   4.1获取头信息
         *   4.2判断是否有该头信息 Authorization
         *   4.3取出头信息token 并使用jwtutil解析
         */

        //5.实现消息头的转发
        requestContext.addZuulRequestHeader("Authorization", header);
        //6.过滤器放行，当返回值是null时放行。
        return null;
    }
}
