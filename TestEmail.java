import com.shopping.util.EmailUtil;

public class TestEmail {
    public static void main(String[] args) {
        System.out.println("=== Testing Email Sending Functionality ===");
        
        // 设置测试参数
        String to = "15616711449@163.com";
        String subject = "测试邮件 - 163邮箱465端口+SSL配置";
        String body = "这是一封测试邮件，用于验证163邮箱465端口+SSL配置是否正常工作。\n\n测试时间：" + new java.util.Date();
        
        // 调用邮件发送方法
        boolean result = EmailUtil.sendEmail(to, subject, body);
        
        System.out.println("\n=== Email Test Result ===");
        System.out.println("Test completed with result: " + result);
        System.out.println("If result is true, email sending was attempted and the system continued operating.");
        System.out.println("Check your email inbox to confirm actual delivery.");
    }
}