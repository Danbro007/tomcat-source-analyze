package com.danbro.servlet;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Classname LoginServlet
 * @Description TODO
 * @Date 2020/11/25 20:12
 * @Author Danrbo
 */
@WebServlet(name = "loginServlet",urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private final static String PASSWORD = "123456";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String username = (String) req.getParameter("username");
        String password = (String) req.getParameter("password");
        HashMap<String, Object> map = new HashMap<>();
        if (PASSWORD.equals(password)){
            map.put("success",true);
            map.put("message","登录成功！");
            req.getSession().setAttribute("username",username);
        }else {
            map.put("success",false);
            map.put("message","用户名或密码错误！");
        }
        resp.getWriter().write(JSON.toJSONString(map));
    }
}
