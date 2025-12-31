<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.shopping.model.OrderItem" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>订单详情</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
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
            <h2>订单详情</h2>
            
            <div class="order-item">
                <div class="order-header">
                    <h3>订单号: ${order.id}</h3>
                    <p>状态: ${order.status}</p>
                </div>
                <p>下单时间: ${order.orderDate}</p>
                <p>支付方式: ${order.paymentMethod}</p>
                <p>收货地址: ${order.shippingAddress}</p>
                
                <div class="order-items">
                    <h4>订单项:</h4>
                    <table>
                        <thead>
                            <tr>
                                <th>商品名称</th>
                                <th>单价</th>
                                <th>数量</th>
                                <th>小计</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% 
                                List<OrderItem> orderItems = ((com.shopping.model.Order)request.getAttribute("order")).getOrderItems();
                                for (OrderItem item : orderItems) {
                            %>
                                <tr>
                                    <td><%= item.getProductName() %></td>
                                    <td>¥<%= item.getUnitPrice() %></td>
                                    <td><%= item.getQuantity() %></td>
                                    <td>¥<%= item.getSubtotal() %></td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
                
                <div class="order-summary">
                    <h3>总价: ¥${order.totalAmount}</h3>
                </div>
                
                <a href="${pageContext.request.contextPath}/orderList" class="btn">返回订单列表</a>
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