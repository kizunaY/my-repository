package com.editor.core.controller;

import com.editor.core.ActionEnter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * UEditor读取配置文件
 */
@Component
public class UEditorHandleInterceptor implements HandlerInterceptor{

    private ThreadLocal<String> exec =new ThreadLocal<>();

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        exec.set(new ActionEnter(request, rootPath).exec());
        OutputStream  writer= response.getOutputStream();
        writer.write(exec.get().getBytes());
        writer.flush();
        writer.close();
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
