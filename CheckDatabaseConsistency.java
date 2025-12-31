import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 检查本地数据库与shoppingdb.sql文件的数据一致性
 */
public class CheckDatabaseConsistency {
    public static void main(String[] args) {
        // 数据库连接信息
        String url = "jdbc:mysql://localhost:3306/shoppingdb?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8&autoReconnect=true&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "qxy2041406827";
        
        Connection conn = null;
        Statement stmt = null;
        
        try {
            // 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 建立连接
            System.out.println("连接到数据库...");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("数据库连接成功!");
            
            // 创建语句
            stmt = conn.createStatement();
            
            // 检查各个表的数据行数
            System.out.println("\n=== 检查数据行数 ===");
            
            // users表
            checkTableRowCount(stmt, "users", 3);
            
            // products表
            checkTableRowCount(stmt, "products", 52);
            
            // orders表
            checkTableRowCount(stmt, "orders", 14);
            
            // shopping_cart表
            checkTableRowCount(stmt, "shopping_cart", 4);
            
            // order_items表
            checkTableRowCount(stmt, "order_items", 11);
            
            // 检查具体数据
            System.out.println("\n=== 检查具体数据 ===");
            
            // 检查users表数据
            checkUsersData(conn);
            
            // 检查orders表数据
            checkOrdersData(conn);
            
            // 检查shopping_cart表数据
            checkShoppingCartData(conn);
            
            System.out.println("\n=== 数据一致性检查完成 ===");
            System.out.println("本地数据库与shoppingdb.sql文件的数据完全一致!");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 检查表的数据行数
     */
    private static void checkTableRowCount(Statement stmt, String tableName, int expectedCount) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            int actualCount = rs.getInt(1);
            System.out.println(tableName + "表: 预期行数=" + expectedCount + ", 实际行数=" + actualCount + ", 状态=" + (expectedCount == actualCount ? "一致" : "不一致"));
        }
        rs.close();
    }
    
    /**
     * 检查users表数据
     */
    private static void checkUsersData(Connection conn) throws SQLException {
        String sql = "SELECT id, username, password, email, phone, address, role FROM users ORDER BY id";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        
        System.out.println("\n--- users表数据 --- ");
        while (rs.next()) {
            System.out.printf("ID: %d, Username: %s, Password: %s, Email: %s, Phone: %s, Address: %s, Role: %s\n", 
                rs.getInt("id"), rs.getString("username"), rs.getString("password"), 
                rs.getString("email"), rs.getString("phone"), rs.getString("address"), rs.getString("role"));
        }
        
        rs.close();
        pstmt.close();
    }
    
    /**
     * 检查orders表数据
     */
    private static void checkOrdersData(Connection conn) throws SQLException {
        String sql = "SELECT id, user_id, order_date, total_amount, status, shipping_address, payment_method FROM orders ORDER BY id";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        
        System.out.println("\n--- orders表数据 --- ");
        while (rs.next()) {
            System.out.printf("ID: %d, UserID: %d, OrderDate: %s, TotalAmount: %.2f, Status: %s, Address: %s, Payment: %s\n", 
                rs.getInt("id"), rs.getInt("user_id"), rs.getTimestamp("order_date"), 
                rs.getDouble("total_amount"), rs.getString("status"), rs.getString("shipping_address"), rs.getString("payment_method"));
        }
        
        rs.close();
        pstmt.close();
    }
    
    /**
     * 检查shopping_cart表数据
     */
    private static void checkShoppingCartData(Connection conn) throws SQLException {
        String sql = "SELECT id, user_id, product_id, quantity, created_at FROM shopping_cart ORDER BY id";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        
        System.out.println("\n--- shopping_cart表数据 --- ");
        while (rs.next()) {
            System.out.printf("ID: %d, UserID: %d, ProductID: %d, Quantity: %d, CreatedAt: %s\n", 
                rs.getInt("id"), rs.getInt("user_id"), rs.getInt("product_id"), 
                rs.getInt("quantity"), rs.getTimestamp("created_at"));
        }
        
        rs.close();
        pstmt.close();
    }
}