<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.shopping.model.Order" %>
<%@ page import="com.shopping.model.OrderItem" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理员 - 订单管理</title>
    <link rel="stylesheet" type="text/css" href="../../css/style.css">
</head>
<body>
    <header>
        <div class="container">
            <h1>在线购物网站 - 管理后台</h1>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/admin/productList">商品管理</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/orderList">订单管理</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/salesReport">销售统计</a></li>
                    <li><a href="${pageContext.request.contextPath}/logout">注销</a></li>
                </ul>
            </nav>
        </div>
    </header>
    
    <div class="container">
        <main>
            <h2>订单管理</h2>
            
            <div class="admin-menu">
                <ul>
                    <li><a href="${pageContext.request.contextPath}/admin/productList">商品管理</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/orderList">订单管理</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/salesReport">销售统计</a></li>
                </ul>
            </div>
            
            <% List<Order> orders = (List<Order>) request.getAttribute("orders");
               if (orders == null || orders.isEmpty()) { %>
                <p>暂无订单</p>
            <% } else { %>
                <div class="order-list">
                    <% for (Order order : orders) { %>
                        <div class="order-item">
                            <div class="order-header">
                                <h3>订单号: <%= order.getId() %></h3>
                                <p>用户ID: <%= order.getUserId() %></p>
                            </div>
                            <p>下单时间: <%= order.getOrderDate() %></p>
                            <p>支付方式: <%= order.getPaymentMethod() %></p>
                            <p>收货地址: <%= order.getShippingAddress() %></p>
                            
                            <div class="order-items">
                                <h4>订单项:</h4>
                                <% for (OrderItem item : order.getOrderItems()) { %>
                                    <div class="order-item-details">
                                        <span><%= item.getProductName() %> x <%= item.getQuantity() %></span>
                                        <span>¥<%= item.getUnitPrice() %> × <%= item.getQuantity() %> = ¥<%= item.getSubtotal() %></span>
                                    </div>
                                <% } %>
                            </div>
                            
                            <div class="order-summary">
                                <p>总价: ¥<%= order.getTotalAmount() %></p>
                                <form action="${pageContext.request.contextPath}/admin/updateOrderStatus" method="post" style="display: inline;">
                                    <input type="hidden" name="orderId" value="<%= order.getId() %>">
                                    <select name="status" onchange="this.form.submit()">
                                        <option value="pending" <%= "pending".equals(order.getStatus()) ? "selected" : "" %>>待处理</option>
                                        <option value="payment" <%= "payment".equals(order.getStatus()) ? "selected" : "" %>>已支付</option>
                                        <option value="shipped" <%= "shipped".equals(order.getStatus()) ? "selected" : "" %>>已发货</option>
                                        <option value="delivered" <%= "delivered".equals(order.getStatus()) ? "selected" : "" %>>已送达</option>
                                    </select>
                                </form>
                            </div>
                        </div>
                    <% } %>
                </div>
            <% } %>
        </main>
    </div>
    
    <footer>
        <div class="container">
            <p>&copy; 2025 在线购物网站 版权所有</p>
        </div>
    </footer>
</body>
</html>