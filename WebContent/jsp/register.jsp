<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户注册</title>
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
            <h2>用户注册</h2>
            
            <% if (request.getAttribute("error") != null) { %>
                <div class="message message-error">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            
            <form action="${pageContext.request.contextPath}/register" method="post">
                <div class="form-group">
                    <label for="username">用户名</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">密码</label>
                    <input type="password" id="password" name="password" required>
                </div>
                
                <div class="form-group">
                    <label for="confirmPassword">确认密码</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required>
                </div>
                
                <div class="password-strength">
                    <p style="font-size: 12px; color: #666; margin-bottom: 15px;">密码要求：至少8个字符，包含大小写字母、数字和特殊字符（如!@#$%^&*等）</p>
                </div>
                <div class="form-group">
                    <label for="email">邮箱</label>
                    <input type="email" id="email" name="email" required>
                </div>
                <div class="form-group">
                    <label for="phone">电话</label>
                    <input type="tel" id="phone" name="phone">
                </div>
                <div class="form-group">
                    <label for="address">地址</label>
                    <textarea id="address" name="address"></textarea>
                </div>
                <div class="form-group">
                    <input type="submit" value="注册" class="btn btn-primary">
                </div>
            </form>
            
            <p>已有账号？<a href="${pageContext.request.contextPath}/login">立即登录</a></p>
        </main>
    </div>
    
    <footer>
        <div class="container">
            <p>&copy; 2025 在线购物网站 版权所有</p>
        </div>
    </footer>
</body>
</html>