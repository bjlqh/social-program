package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 核心方法：
     * 管理员的操作任何时候都需要权限。
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //1.获取请求上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        //2.获取request
        HttpServletRequest request = requestContext.getRequest();

        //特殊情况之：管理员登录没有消息头
        String url = request.getRequestURL().toString();
        if (url.indexOf("/admin/login") > 0) {
            System.out.println("管理员登录: " + url);
            return null;
        }
        //特殊情况之：跨域访问。跨域访问都是两次请求。第一次是预请求：请求方式是options
        String method = request.getMethod();
        if ("OPTIONS".equalsIgnoreCase(method)) {
            System.out.println("跨域请求访问 " + method);
        }

        //3.获取请求消息头
        String header = request.getHeader("Authorization");
        //4.判断是否有消息头，没有就不用在执行了
        if (!StringUtils.isEmpty(header)) {
            //转发消息头
            requestContext.addZuulRequestHeader("Authorization", header);
            //放行
            return null;
        }
        //5.没有消息头就直接响应
        requestContext.setSendZuulResponse(false);//终止执行，无论后面的返回值是什么，都不在执行了
        requestContext.setResponseStatusCode(401);//Unauthority
        requestContext.getResponse().setContentType("application/json;charset=utf-8");//设置响应内容的类型和字符集
        requestContext.setResponseBody("{\"flag\": false,\"code\": 20003,\"message\": \"权限不足\"}");
        return null;
    }
}
