<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.shopping.model.Order" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理员 - 销售统计</title>
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
            <h2>销售统计报表</h2>
            
            <div class="admin-menu">
                <ul>
                    <li><a href="${pageContext.request.contextPath}/admin/productList">商品管理</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/orderList">订单管理</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/salesReport">销售统计</a></li>
                </ul>
            </div>
            
            <div style="margin: 20px 0;">
                <h3>销售概览</h3>
                <div style="display: flex; gap: 20px;">
                    <div style="flex: 1; background-color: #f8f9fa; padding: 15px; border-radius: 5px; text-align: center;">
                        <h4>总销售额</h4>
                        <p style="font-size: 1.5rem; font-weight: bold; color: #28a745;">¥<%= request.getAttribute("totalSales") %></p>
                    </div>
                    <div style="flex: 1; background-color: #f8f9fa; padding: 15px; border-radius: 5px; text-align: center;">
                        <h4>总订单数</h4>
                        <p style="font-size: 1.5rem; font-weight: bold; color: #007bff;"><%= request.getAttribute("totalOrders") %></p>
                    </div>
                    <div style="flex: 1; background-color: #f8f9fa; padding: 15px; border-radius: 5px; text-align: center;">
                        <h4>已发货订单</h4>
                        <p style="font-size: 1.5rem; font-weight: bold; color: #ffc107;"><%= request.getAttribute("shippedOrders") %></p>
                    </div>
                    <div style="flex: 1; background-color: #f8f9fa; padding: 15px; border-radius: 5px; text-align: center;">
                        <h4>已完成订单</h4>
                        <p style="font-size: 1.5rem; font-weight: bold; color: #dc3545;"><%= request.getAttribute("deliveredOrders") %></p>
                    </div>
                </div>
            </div>
            
            <h3>订单明细</h3>
            <table>
                <thead>
                    <tr>
                        <th>订单号</th>
                        <th>用户ID</th>
                        <th>下单时间</th>
                        <th>状态</th>
                        <th>总金额</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <% List<Order> orders = (List<Order>) request.getAttribute("orders");
                       if (orders != null && !orders.isEmpty()) {
                           for (Order order : orders) { %>
                        <tr>
                            <td><%= order.getId() %></td>
                            <td><%= order.getUserId() %></td>
                            <td><%= order.getOrderDate() %></td>
                            <td><%= order.getStatus() %></td>
                            <td>¥<%= order.getTotalAmount() %></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/orderDetail?orderId=<%= order.getId() %>" class="btn">查看详情</a>
                            </td>
                        </tr>
                    <% } 
                       } %>
                </tbody>
            </table>
        </main>
    </div>
    
    <footer>
        <div class="container">
            <p>&copy; 2025 在线购物网站 版权所有</p>
        </div>
    </footer>
</body>
</html>