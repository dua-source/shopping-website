import java.io.File;

public class TestFileNameGeneration {
    
    public static void main(String[] args) {
        // 测试目录
        String testDir = "d:\\Trae CN\\网络应用开发大作业\\WebContent\\images";
        
        // 测试商品名称
        String[] productNames = {
            "iPhone 15 Pro",
            "Samsung Galaxy S24 Ultra",
            "Sony WH-1000XM5 耳机",
            "Apple MacBook Pro 16",
            "iPad Air 5",
            "小米14 Pro",
            "华为Mate 60 Pro",
            "OPPO Find X7",
            "vivo X100 Pro",
            "荣耀Magic6 Pro"
        };
        
        // 测试扩展名
        String[] extensions = {".jpg", ".png", ".gif"};
        
        System.out.println("测试文件名生成逻辑：");
        System.out.println("=========================");
        
        for (String productName : productNames) {
            for (String extension : extensions) {
                // 模拟文件名生成过程
                String sanitizedName = sanitizeFileName(productName);
                String uniqueFileName = getUniqueFileName(sanitizedName, extension, testDir);
                
                System.out.println("商品名称：" + productName);
                System.out.println("处理后：" + sanitizedName);
                System.out.println("最终文件名：" + uniqueFileName);
                System.out.println("-------------------------");
            }
        }
    }
    
    /**
     * 处理文件名中的特殊字符，确保文件名安全
     */
    private static String sanitizeFileName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "default";
        }
        // 替换特殊字符为下划线
        return fileName.replaceAll("[^a-zA-Z0-9_.-]", "_");
    }
    
    /**
     * 确保文件名唯一，如果已存在则添加数字后缀
     */
    private static String getUniqueFileName(String baseName, String extension, String directoryPath) {
        String fileName = baseName + extension;
        File file = new File(directoryPath + File.separator + fileName);
        int counter = 1;
        
        while (file.exists()) {
            fileName = baseName + "_" + counter + extension;
            file = new File(directoryPath + File.separator + fileName);
            counter++;
        }
        
        return fileName;
    }
}