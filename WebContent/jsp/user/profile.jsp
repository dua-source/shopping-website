<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>个人信息</title>
    <link rel="stylesheet" type="text/css" href="../../css/style.css">
</head>
<body>
    <header>
        <div class="container">
            <h1>在线购物网站</h1>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/productList">首页</a></li>
                    <li><a href="${pageContext.request.contextPath}/productList">商品列表</a></li>
                    <li><a href="${pageContext.request.contextPath}/cartList">购物车</a></li>
                    <li><a href="${pageContext.request.contextPath}/orderList">我的订单</a></li>
                    <li><a href="${pageContext.request.contextPath}/updateProfile">个人信息</a></li>
                    <li><a href="${pageContext.request.contextPath}/logout">退出登录</a></li>
                </ul>
            </nav>
        </div>
    </header>
    
    <div class="container">
        <main>
            <h2>个人信息</h2>
            
            <% if (request.getAttribute("successMessage") != null) { %>
                <div class="message message-success">
                    <%= request.getAttribute("successMessage") %>
                </div>
            <% } %>
            
            <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="message message-error">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>
            
            <form action="${pageContext.request.contextPath}/updateProfile" method="post">
                <div class="form-group">
                    <label for="username">用户名</label>
                    <input type="text" id="username" name="username" value="${user.username}" required>
                </div>
                
                <div class="form-group">
                    <label for="email">邮箱</label>
                    <input type="email" id="email" name="email" value="${user.email}" required>
                </div>
                
                <div class="form-group">
                    <label for="phone">电话</label>
                    <input type="tel" id="phone" name="phone" value="${user.phone}">
                </div>
                
                <div class="form-group">
                    <label for="address">地址</label>
                    <textarea id="address" name="address" rows="4">${user.address}</textarea>
                </div>
                
                <div class="form-group">
                    <input type="submit" value="保存修改" class="btn btn-primary">
                </div>
            </form>
            
            <div class="profile-info">
                <h3>账户信息</h3>
                <p>用户ID：${user.id}</p>
                <p>注册时间：${user.createdAt}</p>
                <p>用户角色：${user.role == 'admin' ? '管理员' : '普通用户'}</p>
            </div>
        </main>
    </div>
    
    <footer>
        <div class="container">
            <p>&copy; 2025 在线购物网站 版权所有</p>
        </div>
    </footer>
</body>
</html>