import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RemoveBOMManual {
    
    public static void main(String[] args) {
        try {
            // 手动处理三个关键文件
            removeBOM("src\\com\\shopping\\controller\\RegisterServlet.java");
            removeBOM("src\\com\\shopping\\controller\\OrderDetailServlet.java");
            removeBOM("src\\com\\shopping\\controller\\EditProductServlet.java");
            
            System.out.println("所有文件的BOM字符已移除");
        } catch (Exception e) {
            System.out.println("处理过程中出现错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 移除文件中的BOM字符
     */
    private static void removeBOM(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] content = Files.readAllBytes(path);
        
        // 检查是否包含BOM字符
        if (content.length >= 3 && content[0] == (byte) 0xEF && content[1] == (byte) 0xBB && content[2] == (byte) 0xBF) {
            // 移除BOM字符
            byte[] newContent = new byte[content.length - 3];
            System.arraycopy(content, 3, newContent, 0, newContent.length);
            Files.write(path, newContent);
            System.out.println("已处理 " + filePath);
        } else {
            System.out.println("无需处理 " + filePath);
        }
    }
}