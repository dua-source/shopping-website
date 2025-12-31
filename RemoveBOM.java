import java.io.*;
import java.nio.file.Files;

public class RemoveBOM {
    
    public static void main(String[] args) {
        try {
            // 处理src目录中的文件
            File srcDir = new File("src");
            processDirectory(srcDir);
            
            // 处理WebContent目录中的文件
            File webContentDir = new File("WebContent");
            processDirectory(webContentDir);
            
            System.out.println("所有文件的BOM字符已移除");
        } catch (Exception e) {
            System.out.println("处理过程中出现错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 递归处理目录中的所有Java和JSP文件
     */
    private static void processDirectory(File directory) throws IOException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 递归处理子目录
                    processDirectory(file);
                } else if (file.getName().endsWith(".java") || file.getName().endsWith(".jsp")) {
                    // 处理Java和JSP文件
                    removeBOM(file.getAbsolutePath());
                    System.out.println("已处理 " + file.getPath());
                }
            }
        }
    }
    
    /**
     * 移除文件中的BOM字符
     */
    private static void removeBOM(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] content = Files.readAllBytes(file.toPath());
        
        // 检查是否包含BOM字符
        if (content.length >= 3 && content[0] == (byte) 0xEF && content[1] == (byte) 0xBB && content[2] == (byte) 0xBF) {
            // 移除BOM字符
            byte[] newContent = new byte[content.length - 3];
            System.arraycopy(content, 3, newContent, 0, newContent.length);
            Files.write(file.toPath(), newContent);
        }
    }
}