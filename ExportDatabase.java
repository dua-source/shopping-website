import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExportDatabase {
    
    // 数据库连接信息
    private static final String DB_URL = "jdbc:mysql://localhost:3306/shoppingdb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "qxy2041406827";
    
    // 输出SQL文件路径
    private static final String OUTPUT_FILE = "shoppingdb.sql";
    
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
            System.out.println("数据库连接成功！");
            
            // 创建输出文件
            File output = new File(OUTPUT_FILE);
            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF-8"))) {
                // 1. 写入文件头部
                writer.println("-- 在线购物网站数据库脚本");
                writer.println("-- 生成时间: " + new java.util.Date());
                writer.println();
                
                // 2. 写入删除旧数据库和创建新数据库的语句
                writer.println("-- 1. 删除旧数据库（如果存在）");
                writer.println("DROP DATABASE IF EXISTS shoppingdb;");
                writer.println();
                
                writer.println("-- 2. 创建新数据库");
                writer.println("CREATE DATABASE IF NOT EXISTS shoppingdb DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;");
                writer.println();
                
                writer.println("-- 3. 使用新数据库");
                writer.println("USE shoppingdb;");
                writer.println("-- 设置会话字符集");
                writer.println("SET NAMES utf8mb4;");
                writer.println("SET CHARACTER_SET_CLIENT = utf8mb4;");
                writer.println("SET CHARACTER_SET_RESULTS = utf8mb4;");
                writer.println();
                
                // 3. 获取所有表名
                System.out.println("获取所有表名...");
                stmt = conn.createStatement();
                ResultSet rsTables = stmt.executeQuery("SHOW TABLES");
                List<String> tables = new ArrayList<>();
                while (rsTables.next()) {
                    tables.add(rsTables.getString(1));
                }
                
                // 4. 为每个表生成创建表语句和插入数据语句
                for (String table : tables) {
                    System.out.println("处理表: " + table);
                    
                    // 生成创建表语句
                    ResultSet rsCreate = conn.getMetaData().getTables(null, null, table, new String[]{"TABLE"});
                    if (rsCreate.next()) {
                        writer.println("-- 4. 创建表: " + table);
                        ResultSet rsTableDefinition = stmt.executeQuery("SHOW CREATE TABLE " + table);
                        if (rsTableDefinition.next()) {
                            writer.println(rsTableDefinition.getString(2) + ";");
                            writer.println();
                        }
                    }
                    
                    // 生成插入数据语句
                    ResultSet rsData = stmt.executeQuery("SELECT * FROM " + table);
                    ResultSetMetaData metaData = rsData.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    
                    List<String> insertValues = new ArrayList<>();
                    while (rsData.next()) {
                        StringBuilder values = new StringBuilder("(");
                        for (int i = 1; i <= columnCount; i++) {
                            Object value = rsData.getObject(i);
                            if (value == null) {
                                values.append("NULL");
                            } else {
                                if (value instanceof String || value instanceof java.util.Date) {
                                    values.append("'").append(value.toString().replace("'", "''")).append("'");
                                } else {
                                    values.append(value.toString());
                                }
                            }
                            if (i < columnCount) {
                                values.append(", ");
                            }
                        }
                        values.append(")");
                        insertValues.add(values.toString());
                    }
                    
                    if (!insertValues.isEmpty()) {
                        writer.println("-- 5. 插入数据到表: " + table);
                        writer.println("INSERT INTO " + table + " VALUES");
                        for (int i = 0; i < insertValues.size(); i++) {
                            writer.print("    " + insertValues.get(i));
                            if (i < insertValues.size() - 1) {
                                writer.println(",");
                            } else {
                                writer.println(";\n");
                            }
                        }
                    }
                }
                
                System.out.println("数据库导出完成！");
                System.out.println("导出文件: " + output.getAbsolutePath());
            }
            
        } catch (ClassNotFoundException e) {
            System.out.println("驱动加载失败: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("数据库操作失败: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("文件操作失败: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (pstmt != null) pstmt.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("关闭资源失败: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}