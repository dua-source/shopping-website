<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
    <header>
        <div class="container">
            <h1>在线购物网站</h1>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/productList">首页</a></li>
                    <li><a href="${pageContext.request.contextPath}/login">登录</a></li>
                    <li><a href="${pageContext.request.contextPath}/register">注册</a></li>
                </ul>
            </nav>
        </div>
    </header>
    
    <div class="container">
        <main>
            <h2>用户登录</h2>
            
            <% if (request.getAttribute("error") != null) { %>
                <div class="message message-error">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-group">
                    <label for="username">用户名</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">密码</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="form-group">
                    <input type="submit" value="登录" class="btn btn-primary">
                </div>
            </form>
            
            <p>还没有账号？<a href="${pageContext.request.contextPath}/register">立即注册</a></p>
        </main>
    </div>
    
    <footer>
        <div class="container">
            <p>&copy; 2025 在线购物网站 版权所有</p>
        </div>
    </footer>
</body>
</html>