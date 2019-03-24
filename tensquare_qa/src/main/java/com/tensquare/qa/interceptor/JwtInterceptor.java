package com.tensquare.qa.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class JwtInterceptor extends HandlerInterceptorAdapter {
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            if (authorization.startsWith("Bearer ")) {
                String token = authorization.split(" ")[1];
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    Object roles = claims.get("roles");
                    //把权限都取出
                    if ("ROLE_ADMIN".equals(roles)) {
                        request.setAttribute("admin_claims", claims);
                    }
                    if ("ROLE_USER".equals(roles)) {
                        request.setAttribute("user_claims", claims);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
