import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CopyImagesFromDB {
    
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
                String imagePath = rs.getString("image");
                
                processedCount++;
                
                // 跳过没有图片的商品
                if (imagePath == null || imagePath.isEmpty()) {
                    logMessages.add("商品ID: " + id + " (" + name + ") - 没有图片，跳过");
                    continue;
                }
                
                // 检查图片是否已经在目标文件夹中
                String fileName = new File(imagePath).getName();
                File targetFile = new File(TARGET_DIR + File.separator + fileName);
                
                if (targetFile.exists()) {
                    logMessages.add("商品ID: " + id + " (" + name + ") - 图片已存在，跳过复制: " + fileName);
                    // 更新数据库路径（只保留文件名）
                    updateImagePath(conn, id, fileName);
                    updatedCount++;
                    continue;
                }
                
                try {
                    // 复制图片到目标文件夹
                    File sourceFile = new File(imagePath);
                    if (sourceFile.exists()) {
                        copyFile(sourceFile, targetFile);
                        // 更新数据库路径
                        updateImagePath(conn, id, fileName);
                        logMessages.add("商品ID: " + id + " (" + name + ") - 图片已复制: " + fileName);
                        updatedCount++;
                    } else {
                        logMessages.add("商品ID: " + id + " (" + name + ") - 源图片不存在: " + imagePath);
                    }
                } catch (Exception e) {
                    logMessages.add("商品ID: " + id + " (" + name + ") - 复制失败: " + e.getMessage());
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
     * 更新商品的图片路径
     */
    private static void updateImagePath(Connection conn, int productId, String newImagePath) throws SQLException {
        String updateQuery = "UPDATE product SET image = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(updateQuery);
        pstmt.setString(1, newImagePath);
        pstmt.setInt(2, productId);
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    /**
     * 复制文件
     */
    private static void copyFile(File source, File dest) throws IOException {
        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }
}