import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckDatabase {
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
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("数据库连接成功!");
            
            // 创建语句
            stmt = conn.createStatement();
            
            // 查看products表结构
            System.out.println("\n=== products表结构 ===");
            String sql = "DESCRIBE products";
            ResultSet rs = stmt.executeQuery(sql);
            
            System.out.printf("%-20s %-20s %-10s %-10s %-20s %-10s\n", 
                "字段名", "数据类型", "允许NULL", "主键", "默认值", "额外信息");
            System.out.println("-------------------------------------------------------------------------------------------");
            
            while (rs.next()) {
                String field = rs.getString(1);
                String type = rs.getString(2);
                String nullAllowed = rs.getString(3);
                String key = rs.getString(4);
                String defaultValue = rs.getString(5);
                String extra = rs.getString(6);
                
                System.out.printf("%-20s %-20s %-10s %-10s %-20s %-10s\n", 
                    field, type, nullAllowed, key, defaultValue != null ? defaultValue : "NULL", extra != null ? extra : "");
            }
            rs.close();
            
            // 查看products表内容
            System.out.println("\n=== products表内容（前5条）===");
            sql = "SELECT * FROM products LIMIT 5";
            rs = stmt.executeQuery(sql);
            
            // 获取列名
            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-15s", rs.getMetaData().getColumnName(i));
            }
            System.out.println();
            System.out.println("-------------------------------------------------------------------------------------------");
            
            // 输出数据
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String value = rs.getString(i);
                    System.out.printf("%-15s", value != null ? value : "NULL");
                }
                System.out.println();
            }
            rs.close();
            
            // 查看Product类的属性（通过反射）
            System.out.println("\n=== Product类属性 ===");
            Class<?> productClass = Class.forName("com.shopping.model.Product");
            java.lang.reflect.Field[] fields = productClass.getDeclaredFields();
            for (java.lang.reflect.Field field : fields) {
                System.out.printf("%-20s %-20s%n", field.getType().getSimpleName(), field.getName());
            }
            
            System.out.println("\n=== 检查完成 ===");
            System.out.println("请根据以上信息检查Product类与数据库表结构是否匹配。");
            
        } catch (Exception e) {
            System.err.println("错误: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}