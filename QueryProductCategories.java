import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class QueryProductCategories {
    public static void main(String[] args) {
        // 数据库连接信息
        String url = "jdbc:mysql://localhost:3306/shoppingdb?useSSL=false&serverTimezone=UTC&characterEncoding=utf8&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "qxy2041406827";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 建立连接
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("数据库连接成功!");
            
            // 查询所有商品的ID、名称和分类
            String sql = "SELECT id, name, category FROM products ORDER BY category, id";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            System.out.println("\n当前商品分类情况:");
            System.out.println("ID | 商品名称 | 当前分类");
            System.out.println("----------------------------------------");
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                
                System.out.printf("%3d | %-20s | %s\n", id, name, category);
            }
            
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL驱动加载失败: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("操作失败: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}