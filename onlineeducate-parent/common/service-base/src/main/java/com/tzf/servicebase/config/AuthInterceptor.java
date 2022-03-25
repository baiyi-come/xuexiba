package com.tzf.servicebase.config;


import com.tzf.commonutil.JwtUtils;
import com.tzf.servicebase.exceptionHandler.GlobalCommonExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {


        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //放行逻辑
        HandlerMethod method = (HandlerMethod) handler;
        DisableAuth auth = method.getMethod().getAnnotation(DisableAuth.class);
        if (isDisableAuth(auth)) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        boolean checkToken = JwtUtils.checkToken(request);
        if (checkToken == false) {

           throw new GlobalCommonExceptionHandler(20004, "请登陆后再访问");
        }

        return true;
    }

    private static boolean isDisableAuth(DisableAuth auth) {
        return auth != null;
    }

    /**
     * 获取http请求头部或者参数中的token值
     *
     * @param request
     *            http请求传递的值
     * @return 返回token
     */
    private String getAuthToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        System.out.println("=========="+token);
        if (token == null) {
            token = request.getParameter("token");
        }
        return token;
    }
}
