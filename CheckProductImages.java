import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckProductImages {
    public static void main(String[] args) {
        // 数据库连接信息
        String url = "jdbc:mysql://localhost:3306/shoppingdb?useSSL=false&serverTimezone=UTC&characterEncoding=utf8&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "qxy2041406827";
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            // 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 建立连接
            conn = DriverManager.getConnection(url, username, password);
            
            // 创建语句
            stmt = conn.createStatement();
            
            // 查询所有产品
            String sql = "SELECT id, name, image FROM products";
            rs = stmt.executeQuery(sql);
            
            // 输出结果
            System.out.println("=== 产品图片检查结果 ===");
            System.out.printf("%-5s %-20s %-30s\n", "ID", "产品名称", "图片路径");
            System.out.println("-------------------------------------------");
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String image = rs.getString("image");
                
                System.out.printf("%-5d %-20s %-30s\n", id, name, image);
            }
            
            System.out.println("-------------------------------------------");
            System.out.println("检查完成。如果图片路径为空或无效，产品图片将无法显示。");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}