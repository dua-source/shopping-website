package com.shopping.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil {

    // 邮件发送开关，默认为true
    private static boolean emailEnabled = true;

    // 网络连接测试方法
    private static boolean testNetworkConnection(String host, int port, int timeout) {
        System.out.println("=== Network Connection Test ===");
        System.out.println("Testing connection to: " + host + ":" + port);
        System.out.println("Timeout: " + timeout + "ms");
        try (Socket socket = new Socket()) {
            socket.connect(new java.net.InetSocketAddress(host, port), timeout);
            System.out.println("✓ SUCCESS: Network connection to " + host + ":" + port + " is available");
            System.out.println("=== Network Test Completed ===");
            return true;
        } catch (java.net.ConnectException e) {
            System.err.println("✗ ERROR: Connection refused to " + host + ":" + port);
            System.err.println("Possible causes: Server down, port blocked, firewall issue");
            System.err.println("Exception: " + e.getMessage());
            System.err.println("=== Network Test Failed ===");
            return false;
        } catch (SocketTimeoutException e) {
            System.err.println("✗ ERROR: Connection timed out to " + host + ":" + port);
            System.err.println("Possible causes: Network delay, server busy");
            System.err.println("Exception: " + e.getMessage());
            System.err.println("=== Network Test Failed ===");
            return false;
        } catch (Exception e) {
            System.err.println("✗ ERROR: Network connection to " + host + ":" + port + " failed!");
            System.err.println("Exception type: " + e.getClass().getName());
            System.err.println("Exception message: " + e.getMessage());
            System.err.println("=== Network Test Failed ===");
            return false;
        }
    }

    private static Properties getMailProperties() {
        System.out.println("=== Loading Mail Properties ===");
        Properties properties = new Properties();
        try (InputStream input = EmailUtil.class.getClassLoader().getResourceAsStream("mail.properties")) {
            if (input == null) {
                System.err.println("✗ ERROR: Unable to find mail.properties file in classpath");
                System.err.println("Please check:");
                System.err.println("1. mail.properties exists in src/ directory");
                System.err.println("2. File was copied to WEB-INF/classes/ during compilation");
                System.err.println("3. Classpath is correctly configured");
                System.err.println("=== Properties Loading Failed ===");
                return null;
            }
            properties.load(input);
            
            // 加载邮件发送开关配置
            String enabledConfig = properties.getProperty("mail.enabled");
            if (enabledConfig != null) {
                emailEnabled = Boolean.parseBoolean(enabledConfig);
            }
            
            System.out.println("=== Mail Configuration ===");
            System.out.println("Email Enabled: " + (emailEnabled ? "✓ YES" : "✗ NO"));
            System.out.println("SMTP Host: " + properties.getProperty("mail.smtp.host"));
            System.out.println("SMTP Port: " + properties.getProperty("mail.smtp.port"));
            System.out.println("Auth Required: " + properties.getProperty("mail.smtp.auth"));
            System.out.println("SSL Enabled: " + properties.getProperty("mail.smtp.ssl.enable"));
            System.out.println("SSL Protocols: " + properties.getProperty("mail.smtp.ssl.protocols"));
            System.out.println("Socket Factory: " + properties.getProperty("mail.smtp.socketFactory.class"));
            System.out.println("Retry Count: " + properties.getProperty("mail.retry.count"));
            System.out.println("Retry Delay: " + properties.getProperty("mail.retry.delay") + "ms");
            System.out.println("Debug Mode: " + properties.getProperty("mail.smtp.debug"));
            System.out.println("From Address: " + properties.getProperty("mail.from"));
            System.out.println("Username: " + properties.getProperty("mail.smtp.username"));
            System.out.println("=====================");
            System.out.println("✓ SUCCESS: Mail properties loaded successfully");
            System.out.println("=== Properties Loading Completed ===");
        } catch (IOException ex) {
            System.err.println("✗ ERROR: Failed to load mail.properties file");
            System.err.println("Possible causes: File corrupted, wrong encoding");
            System.err.println("Exception: " + ex.getMessage());
            ex.printStackTrace();
            System.err.println("=== Properties Loading Failed ===");
            return null;
        }
        return properties;
    }

    public static boolean sendEmail(String to, String subject, String body) {
        System.out.println("\n=================================================================");
        System.out.println("=== Starting Email Sending Process ===");
        System.out.println("=================================================================");
        System.out.println("Recipient: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Sending Time: " + new java.util.Date());
        
        // 检查邮件发送开关
        if (!emailEnabled) {
            System.out.println("INFO: Email sending is disabled, skipping email sending");
            System.out.println("=================================================================");
            System.out.println("=== Email Sending Process Skipped ===");
            System.out.println("=================================================================");
            return true; // 返回true表示流程正常，只是跳过了邮件发送
        }
        
        Properties properties = getMailProperties();
        if (properties == null) {
            System.err.println("✗ CRITICAL ERROR: Mail properties are null, cannot send email");
            System.err.println("=================================================================");
            System.err.println("=== Email Sending Process Failed ===");
            System.err.println("=================================================================");
            return false;
        }

        String username = properties.getProperty("mail.smtp.username");
        String password = properties.getProperty("mail.smtp.password");
        String from = properties.getProperty("mail.from");
        String host = properties.getProperty("mail.smtp.host");
        int port = Integer.parseInt(properties.getProperty("mail.smtp.port", "465"));
        int timeout = Integer.parseInt(properties.getProperty("mail.smtp.connectiontimeout", "30000"));
        int retryCount = Integer.parseInt(properties.getProperty("mail.retry.count", "5"));
        int retryDelay = Integer.parseInt(properties.getProperty("mail.retry.delay", "2000"));
        
        // 验证关键配置
        System.out.println("\n=== Validating Email Configuration ===");
        if (username == null || username.isEmpty()) {
            System.err.println("✗ ERROR: SMTP username is empty");
            System.err.println("Please check mail.properties -> mail.smtp.username");
            System.err.println("=================================================================");
            System.err.println("=== Email Sending Process Failed ===");
            System.err.println("=================================================================");
            return false;
        }
        if (password == null || password.isEmpty()) {
            System.err.println("✗ ERROR: SMTP password (authorization code) is empty");
            System.err.println("Please check mail.properties -> mail.smtp.password");
            System.err.println("Note: Password should be the authorization code generated in 163 mailbox settings");
            System.err.println("=================================================================");
            System.err.println("=== Email Sending Process Failed ===");
            System.err.println("=================================================================");
            return false;
        }
        if (from == null || from.isEmpty()) {
            System.err.println("✗ ERROR: From address is empty");
            System.err.println("Please check mail.properties -> mail.from");
            System.err.println("=================================================================");
            System.err.println("=== Email Sending Process Failed ===");
            System.err.println("=================================================================");
            return false;
        }
        if (host == null || host.isEmpty()) {
            System.err.println("✗ ERROR: SMTP host is empty");
            System.err.println("Please check mail.properties -> mail.smtp.host");
            System.err.println("=================================================================");
            System.err.println("=== Email Sending Process Failed ===");
            System.err.println("=================================================================");
            return false;
        }
        
        System.out.println("✓ Username: " + username);
        System.out.println("✓ From Address: " + from);
        System.out.println("✓ SMTP Server: " + host + ":" + port);
        System.out.println("✓ Retry Configuration: " + retryCount + " attempts, " + retryDelay + "ms delay");
        System.out.println("✓ Timeout: " + timeout + "ms");
        System.out.println("=== Configuration Validation Completed ===");

        // 测试网络连接，带有重试机制
        System.out.println("\n=== Testing Network Connection ===");
        boolean networkAvailable = false;
        for (int i = 0; i < retryCount; i++) {
            System.out.println("\nNetwork test attempt " + (i + 1) + " of " + retryCount);
            networkAvailable = testNetworkConnection(host, port, timeout);
            if (networkAvailable) {
                System.out.println("✓ Network connection test passed!");
                break;
            }
            if (i < retryCount - 1) {
                System.out.println("⏳ Network test failed, retrying in " + retryDelay + "ms...");
                try {
                    Thread.sleep(retryDelay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("✗ Network test interrupted");
                    break;
                }
            }
        }
        
        if (!networkAvailable) {
            System.err.println("\n⚠️ WARNING: Network connection failed after " + retryCount + " attempts");
            System.err.println("Note: System will continue with other operations, skipping email sending");
            System.err.println("=================================================================");
            System.err.println("=== Email Sending Process Failed ===");
            System.err.println("=================================================================");
            // 网络连接失败时，返回true表示流程继续执行，只是跳过了邮件发送
            return true;
        }

        // 设置Session属性
        System.out.println("\n=== Creating SMTP Session ===");
        System.out.println("Initializing JavaMail Session...");
        System.out.println("Debug mode: " + properties.getProperty("mail.smtp.debug"));
        
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                System.out.println("🔐 Authenticating with SMTP server...");
                System.out.println("Username: " + username);
                System.out.println("Password: [hidden for security]");
                return new PasswordAuthentication(username, password);
            }
        });

        // 启用调试模式
        session.setDebug(true);
        System.out.println("✓ SMTP Session created successfully");

        // 邮件发送重试机制
        System.out.println("\n=== Email Sending Attempts ===");
        System.out.println("Total attempts: " + retryCount);
        System.out.println("Retry delay: " + retryDelay + "ms");
        
        boolean sendSuccess = false;
        for (int attempt = 1; attempt <= retryCount; attempt++) {
            try {
                System.out.println("\n🚀 Email Send Attempt " + attempt + " of " + retryCount);
                System.out.println("Timestamp: " + new java.util.Date());
                
                // 创建邮件消息
                System.out.println("📝 Creating MimeMessage...");
                MimeMessage message = new MimeMessage(session);
                
                // 设置发件人
                System.out.println("✉️  Setting From address: " + from);
                message.setFrom(new InternetAddress(from, "在线购物系统", "UTF-8"));
                
                // 设置收件人
                System.out.println("📋 Setting To address: " + to);
                InternetAddress[] toAddresses = InternetAddress.parse(to);
                message.setRecipients(Message.RecipientType.TO, toAddresses);
                
                // 设置邮件主题（使用UTF-8编码）
                System.out.println("📌 Setting Subject: " + subject);
                message.setSubject(subject, "UTF-8");
                
                // 设置邮件内容（使用UTF-8编码）
                System.out.println("📄 Setting Content...");
                message.setText(body, "UTF-8");
                
                // 设置邮件发送时间
                message.setSentDate(new java.util.Date());
                
                System.out.println("\n📤 Sending email...");
                System.out.println("=== Email Details ===");
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("To: " + message.getRecipients(Message.RecipientType.TO)[0]);
                System.out.println("Subject: " + message.getSubject());
                System.out.println("Timeout: " + timeout + "ms");
                System.out.println("====================");
                
                // 发送邮件
                Transport.send(message);
                
                System.out.println("🎉 SUCCESS: Email sent successfully!");
                System.out.println("Recipient: " + to);
                System.out.println("Attempt: " + attempt + " of " + retryCount);
                System.out.println("=================================================================");
                System.out.println("=== Email Sending Process Completed Successfully ===");
                System.out.println("=================================================================");
                sendSuccess = true;
                return true;
            } catch (AddressException e) {
                System.err.println("\n✗ ERROR: Invalid email address format (attempt " + attempt + ")");
                System.err.println("Recipient: " + to);
                System.err.println("Exception: " + e.getMessage());
                System.err.println("Fix: Please check the email address format");
                e.printStackTrace();
                break; // 地址错误不需要重试
            } catch (AuthenticationFailedException e) {
                System.err.println("\n✗ ERROR: SMTP Authentication Failed! (attempt " + attempt + ")");
                System.err.println("Possible causes:");
                System.err.println("1. Invalid username or password (authorization code)");
                System.err.println("2. Authorization code expired");
                System.err.println("3. SMTP service not enabled in mailbox settings");
                System.err.println("Username: " + username);
                System.err.println("Password: [hidden]");
                System.err.println("Exception: " + e.getMessage());
                System.err.println("Fix: Generate a new authorization code in 163 mailbox settings");
                e.printStackTrace();
                break; // 认证失败不需要重试
            } catch (MessagingException e) {
                System.err.println("\n✗ ERROR: Messaging exception (attempt " + attempt + ")");
                System.err.println("Exception type: " + e.getClass().getName());
                System.err.println("Exception message: " + e.getMessage());
                e.printStackTrace();
                
                // 检查是否是网络相关异常，如超时、连接问题等
                boolean isNetworkException = false;
                
                // 检查异常消息中是否包含网络相关关键词
                if (e.getMessage() != null) {
                    isNetworkException = e.getMessage().contains("timeout") || 
                                        e.getMessage().contains("Connection") || 
                                        e.getMessage().contains("Socket") ||
                                        e.getMessage().contains("read failed") ||
                                        e.getMessage().contains("write failed") ||
                                        e.getMessage().contains("SSL") ||
                                        e.getMessage().contains("TLS");
                }
                
                // 检查异常原因链中是否包含网络相关异常
                Throwable cause = e.getCause();
                while (cause != null) {
                    System.err.println("Cause: " + cause.getClass().getName() + ": " + cause.getMessage());
                    if (cause instanceof SocketTimeoutException || 
                        cause instanceof java.net.SocketException ||
                        cause instanceof java.io.IOException ||
                        cause instanceof javax.net.ssl.SSLException) {
                        isNetworkException = true;
                        break;
                    }
                    cause = cause.getCause();
                }
                
                if (isNetworkException) {
                    System.out.println("🔄 Network-related exception, will retry...");
                    if (attempt < retryCount) {
                        System.out.println("⏳ Retrying in " + retryDelay + "ms...");
                        try {
                            Thread.sleep(retryDelay);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            System.err.println("✗ Retry interrupted");
                            break;
                        }
                    }
                } else {
                    System.err.println("💥 Non-network exception, no retry needed");
                    break; // 非网络相关的消息异常，不需要重试
                }
            } catch (Exception e) {
                System.err.println("\n✗ ERROR: Unexpected error (attempt " + attempt + ")");
                System.err.println("Exception type: " + e.getClass().getName());
                System.err.println("Exception message: " + e.getMessage());
                e.printStackTrace();
                
                // 其他异常，根据类型决定是否重试
                boolean isNetworkException = false;
                
                // 检查是否是网络相关异常
                if (e instanceof SocketTimeoutException || 
                    e instanceof java.net.SocketException ||
                    e instanceof java.io.IOException ||
                    e instanceof javax.net.ssl.SSLException) {
                    isNetworkException = true;
                }
                
                // 检查异常消息中是否包含网络相关关键词
                if (!isNetworkException && e.getMessage() != null) {
                    isNetworkException = e.getMessage().contains("timeout") || 
                                        e.getMessage().contains("Connection") || 
                                        e.getMessage().contains("Socket") ||
                                        e.getMessage().contains("SSL") ||
                                        e.getMessage().contains("TLS");
                }
                
                if (isNetworkException) {
                    System.out.println("🔄 Network-related exception, will retry...");
                    if (attempt < retryCount) {
                        System.out.println("⏳ Retrying in " + retryDelay + "ms...");
                        try {
                            Thread.sleep(retryDelay);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            System.err.println("✗ Retry interrupted");
                            break;
                        }
                    }
                } else {
                    System.err.println("💥 Unexpected exception, no retry needed");
                    break; // 非网络相关的异常，不需要重试
                }
            }
        }
        
        System.out.println("\n=================================================================");
        System.out.println("=== Email Sending Process Failed After " + retryCount + " Attempts ===");
        System.out.println("=================================================================");
        System.out.println("📋 Failure Summary:");
        System.out.println("- Recipient: " + to);
        System.out.println("- Subject: " + subject);
        System.out.println("- Attempts: " + retryCount);
        System.out.println("- Status: FAILED");
        System.out.println("\n💡 Possible Solutions:");
        System.out.println("1. Check 163 mailbox SMTP settings");
        System.out.println("2. Generate a new authorization code");
        System.out.println("3. Verify network connectivity");
        System.out.println("4. Check firewall settings");
        System.out.println("5. Verify JavaMail API compatibility");
        System.out.println("\n📝 Note: System will continue with other operations");
        System.out.println("=================================================================");
        // 邮件发送失败时，返回true表示流程继续执行
        return true;
    }
}