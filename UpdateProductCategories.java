import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateProductCategories {
    public static void main(String[] args) {
        // 数据库连接信息
        String url = "jdbc:mysql://localhost:3306/shoppingdb?useSSL=false&serverTimezone=UTC&characterEncoding=utf8&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "qxy2041406827";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            // 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 建立连接
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("数据库连接成功!");
            
            // 设置自动提交为false，以便事务管理
            conn.setAutoCommit(false);
            
            // 准备update语句
            String sql = "UPDATE products SET category = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            
            // 1. 更新手机分类（保持不变）
            // iPhone 15 (id:1), Samsung Galaxy S24 (id:2), 小米14 Pro (id:42), 荣耀Magic6 Pro (id:43), 一加12 (id:44), vivo X100 Pro (id:45)
            // 这些已经是手机分类，不需要修改
            
            // 2. 更新平板分类
            String[] tabletIds = {"7", "8", "9", "10", "11"}; // iPad Air 6, 华为MatePad, 小米Pad, 三星Galaxy Tab, 联想小新Pad
            for (String id : tabletIds) {
                pstmt.setString(1, "平板");
                pstmt.setInt(2, Integer.parseInt(id));
                pstmt.addBatch();
            }
            
            // 3. 更新智能手表分类
            String[] watchIds = {"12", "13", "14", "15", "16"}; // Apple Watch, 华为Watch, 小米Watch, 三星Galaxy Watch, Garmin
            for (String id : watchIds) {
                pstmt.setString(1, "智能手表");
                pstmt.setInt(2, Integer.parseInt(id));
                pstmt.addBatch();
            }
            
            // 4. 更新相机分类
            String[] cameraIds = {"17", "18", "19", "20", "21"}; // 佳能EOS R5, 索尼A7 IV, 尼康Z8, 富士X-T5, 松下Lumix S5 II
            for (String id : cameraIds) {
                pstmt.setString(1, "相机");
                pstmt.setInt(2, Integer.parseInt(id));
                pstmt.addBatch();
            }
            
            // 5. 更新音箱分类
            String[] speakerIds = {"22", "23", "24", "25", "26"}; // HomePod 2, 华为Sound X, 小米Sound Pro, 索尼SRS-RA5000, Bose Home Speaker
            for (String id : speakerIds) {
                pstmt.setString(1, "音箱");
                pstmt.setInt(2, Integer.parseInt(id));
                pstmt.addBatch();
            }
            
            // 6. 更新游戏机分类
            String[] gameIds = {"27", "28", "29", "30"}; // PlayStation 5 Pro, Xbox Series X, Nintendo Switch OLED, Steam Deck
            for (String id : gameIds) {
                pstmt.setString(1, "游戏机");
                pstmt.setInt(2, Integer.parseInt(id));
                pstmt.addBatch();
            }
            
            // 7. 更新智能家居分类
            String[] smartHomeIds = {"32", "33", "34", "35", "36"}; // 小米智能门锁Pro, 华为智能摄像头, 天猫精灵CC10, 绿米Aqara智能窗帘电机, 海尔智能空调
            for (String id : smartHomeIds) {
                pstmt.setString(1, "智能家居");
                pstmt.setInt(2, Integer.parseInt(id));
                pstmt.addBatch();
            }
            
            // 8. 更新办公设备分类
            String[] officeIds = {"36", "39", "40", "41"}; // 海尔智能空调, 爱普生L3256打印机, 得力A3激光打印机, 联想ThinkVision显示器
            for (String id : officeIds) {
                pstmt.setString(1, "办公设备");
                pstmt.setInt(2, Integer.parseInt(id));
                pstmt.addBatch();
            }
            
            // 9. 更新外设配件分类
            String[] peripheralIds = {"31", "37"}; // 罗技G923赛车方向盘, 罗技MX Master 3S鼠标
            for (String id : peripheralIds) {
                pstmt.setString(1, "外设配件");
                pstmt.setInt(2, Integer.parseInt(id));
                pstmt.addBatch();
            }
            
            // 执行批处理
            int[] results = pstmt.executeBatch();
            System.out.println("更新了 " + results.length + " 条记录");
            
            // 提交事务
            conn.commit();
            System.out.println("分类更新成功!");
            
            // 验证更新结果
            System.out.println("\n更新后的分类情况:");
            String querySql = "SELECT DISTINCT category FROM products ORDER BY category";
            PreparedStatement queryStmt = conn.prepareStatement(querySql);
            ResultSet rs = queryStmt.executeQuery();
            System.out.println("所有分类:");
            while (rs.next()) {
                System.out.println("- " + rs.getString("category"));
            }
            rs.close();
            queryStmt.close();
            
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL驱动加载失败: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("操作失败: " + e.getMessage());
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            // 关闭资源
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}