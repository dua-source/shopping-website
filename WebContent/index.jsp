<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>在线购物网站</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <header>
        <div class="container">
            <h1>在线购物网站</h1>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/productList">首页</a></li>
                    <% if (session.getAttribute("user") == null) { %>
                        <li><a href="${pageContext.request.contextPath}/login">登录</a></li>
                        <li><a href="${pageContext.request.contextPath}/register">注册</a></li>
                    <% } else { %>
                        <li><a href="${pageContext.request.contextPath}/cartList">购物车</a></li>
                        <li><a href="${pageContext.request.contextPath}/orderList">我的订单</a></li>
                        <% if ("admin".equals(((com.shopping.model.User) session.getAttribute("user")).getRole())) { %>
                            <li><a href="${pageContext.request.contextPath}/admin/productList">管理后台</a></li>
                        <% } %>
                        <li><a href="${pageContext.request.contextPath}/logout">注销</a></li>
                    <% } %>
                </ul>
            </nav>
        </div>
    </header>
    
    <div class="container">
        <main>
            <h2>欢迎来到在线购物网站</h2>
            <p>这里有各种各样的商品供您选择，快来选购吧！</p>
            <a href="${pageContext.request.contextPath}/productList" class="btn btn-primary">开始购物</a>
        </main>
    </div>
    
    <footer>
        <div class="container">
            <p>&copy; 2025 在线购物网站 版权所有</p>
        </div>
    </footer>
</body>
</html>