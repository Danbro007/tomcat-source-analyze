<%--
  Created by IntelliJ IDEA.
  User: liweimo
  Date: 2020/11/24
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Title</title></head>
<body>TOMCAT ‐ 8180 ： <br/> sessionID : <%= session.getId()%>
<br/> <% Object loginUser = session.getAttribute("loginUser");
    if (loginUser != null && loginUser.toString().length() > 0) {
        out.println("session 有值, loginUser = " + loginUser);
    } else {
        session.setAttribute("loginUser", "ITCAST");
        out.println("session 没有值");
    } %></body>
</html>
