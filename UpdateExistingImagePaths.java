import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UpdateExistingImagePaths {
    
    // 数据库连接信息
    private static final String DB_URL = "jdbc:mysql://localhost:3306/shoppingdb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "qxy2041406827";
    
    // 目标文件夹路径
    private static final String TARGET_DIR = "d:\\Trae CN\\网络应用开发大作业\\WebContent\\images";
    
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        
        try {
            // 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 连接数据库
            System.out.println("连接到数据库...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            conn.setAutoCommit(false); // 开始事务
            
            // 查询所有商品
            System.out.println("查询所有商品...");
            String query = "SELECT id, name, image FROM products";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            List<String> logMessages = new ArrayList<>();
            int processedCount = 0;
            int updatedCount = 0;
            
            // 处理每一条记录
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String currentImage = rs.getString("image");
                
                processedCount++;
                
                // 为商品生成新的图片文件名（基于商品名称）
                String newImageName = generateImageFileName(name);
                
                // 如果当前图片已经是新的格式，则跳过
                if (currentImage != null && currentImage.equals(newImageName)) {
                    logMessages.add("商品ID: " + id + " (" + name + ") - 图片名称已正确，跳过");
                    continue;
                }
                
                // 更新数据库中的图片路径
                try {
                    updateImagePath(conn, id, newImageName);
                    logMessages.add("商品ID: " + id + " (" + name + ") - 图片路径已更新为: " + newImageName);
                    updatedCount++;
                } catch (Exception e) {
                    logMessages.add("商品ID: " + id + " (" + name + ") - 更新失败: " + e.getMessage());
                }
            }
            
            // 提交事务
            conn.commit();
            
            // 输出结果
            System.out.println("处理完成！");
            System.out.println("总商品数: " + processedCount);
            System.out.println("更新商品数: " + updatedCount);
            System.out.println("详细日志:");
            for (String log : logMessages) {
                System.out.println(log);
            }
            
        } catch (ClassNotFoundException e) {
            System.out.println("驱动加载失败: " + e.getMessage());
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) {}
            }
        } catch (SQLException e) {
            System.out.println("数据库操作失败: " + e.getMessage());
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) {}
            }
        } catch (Exception e) {
            System.out.println("操作失败: " + e.getMessage());
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) {}
            }
        } finally {
            // 关闭资源
            try {
                if (pstmt != null) pstmt.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("关闭资源失败: " + e.getMessage());
            }
        }
    }
    
    /**
     * 生成图片文件名：使用商品名称，处理特殊字符，确保安全
     */
    private static String generateImageFileName(String productName) {
        if (productName == null || productName.isEmpty()) {
            return "default.jpg";
        }
        // 替换特殊字符为下划线
        String sanitizedName = productName.replaceAll("[^a-zA-Z0-9_.-]", "_");
        // 添加默认扩展名
        return sanitizedName + ".jpg";
    }
    
    /**
     * 更新商品的图片路径
     */
    private static void updateImagePath(Connection conn, int productId, String newImagePath) throws SQLException {
        String updateQuery = "UPDATE products SET image = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(updateQuery);
        pstmt.setString(1, newImagePath);
        pstmt.setInt(2, productId);
        pstmt.executeUpdate();
        pstmt.close();
    }
}