package com.shopping.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private static String url;
    private static String username;
    private static String password;
    private static String driver;
    
    // 静态代码块，加载配置文件和驱动
    static {
        try {
            // 加载配置文件
            InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            Properties props = new Properties();
            props.load(is);
            
            // 获取配置信息
            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
            driver = props.getProperty("db.driver");
            
            // 加载驱动
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 获取数据库连接
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
    
    // 关闭资源
    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // 关闭资源（重载方法，用于没有ResultSet的情况）
    public static void close(Connection conn, PreparedStatement pstmt) {
        close(conn, pstmt, null);
    }
}

