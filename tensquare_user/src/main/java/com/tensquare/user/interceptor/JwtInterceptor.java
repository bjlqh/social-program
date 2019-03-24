package com.tensquare.user.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 */
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 在控制器方法执行前，拦截器方法先执行。
     *
     * @param request
     * @param response
     * @param handler
     * @return 当返回值为true时拦截器放行
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器预处理");
        //1.获取头信息
        String authorization = request.getHeader("Authorization");
        //2.判断是否有该消息头
        if (authorization != null) {
            if (authorization.startsWith("Bearer ")) {
                String[] split = authorization.split(" ");
                String token = split[1];
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    if (claims != null) {
                        String roles = (String) claims.get("roles");
                        if ("ROLE_ADMIN".equals(roles)) {
                            request.setAttribute("admin_claims", claims);
                        }
                        if ("ROLE_USER".equals(roles)) {
                            request.setAttribute("user_claims", claims);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("有机会修改ModelAndView");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("可以根据ex是否为null判断是否发生了异常，进行日志记录");
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("不知道你是干嘛的");
    }
}
