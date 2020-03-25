package com.llb.interception;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 驾校管理系统拦截器：验证用户是否登录，如果没有登录则重定向到login.html页面
 * @Author llb
 * Date on 2020/3/25
 */
public class LoginIntercept implements HandlerInterceptor{

    /**
     * 逻辑判断，通过获取session来判断用户是否登录，如果登录放行，如果没有登录则拦截
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession();

        //获取session
        Object student = session.getAttribute("student");
        Object admin = session.getAttribute("admin");
        Object teacher = session.getAttribute("teacher");


        try {
            if(student == null && admin == null && teacher == null) {
                request.getRequestDispatcher(request.getContextPath()+"/login.html").forward(request, response);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }
}
