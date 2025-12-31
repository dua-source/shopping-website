-- 在线购物网站数据库脚本
-- 生成时间: Wed Dec 31 15:50:00 CST 2025

-- 1. 删除旧数据库（如果存在）
DROP DATABASE IF EXISTS shoppingdb;

-- 2. 创建新数据库
CREATE DATABASE IF NOT EXISTS shoppingdb DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 3. 使用新数据库
USE shoppingdb;
-- 设置会话字符集
SET NAMES utf8mb4;
SET CHARACTER_SET_CLIENT = utf8mb4;
SET CHARACTER_SET_RESULTS = utf8mb4;

-- 4. 创建表: users
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'customer',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. 创建表: products
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `stock` int NOT NULL DEFAULT '0',
  `category` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `image` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. 创建表: orders
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `order_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total_amount` decimal(10,2) NOT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'pending',
  `shipping_address` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `payment_method` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. 创建表: shopping_cart
CREATE TABLE `shopping_cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_product` (`user_id`,`product_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `shopping_cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `shopping_cart_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. 创建表: order_items
CREATE TABLE `order_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE,
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5. 插入数据到表: users
INSERT INTO users VALUES
    (1, 'admin', 'admin123', 'admin@example.com', '13800138000', 'Beijing', 'admin', '2025-12-28 06:33:26.0'),
    (2, 'user1', 'user123', 'user1@example.com', '13900139000', 'Shanghai', 'customer', '2025-12-28 06:33:26.0'),
    (3, 'dua', 'qxy2041406827', '2041406827@qq.com', '15616711449', 'SCUTfinance', 'customer', '2025-12-30 04:50:25.0');

-- 5. 插入数据到表: products
INSERT INTO products VALUES
    (1, 'iPhone 15', '最新款iPhone，搭载A17 Pro芯片', 7999.00, 100, '手机', 'iPhone_15.jpg', '2025-12-30 02:36:32.0'),
    (2, 'Samsung Galaxy S24', '三星旗舰手机，搭载骁龙8 Gen 3芯片', 6999.00, 80, '手机', 'Samsung_Galaxy_S24.jpg', '2025-12-30 02:36:32.0'),
    (3, 'MacBook Pro 14', '苹果笔记本电脑，M3 Pro芯片', 15999.00, 50, '电脑', 'MacBook_Pro_14.jpg', '2025-12-30 02:36:32.0'),
    (4, 'ThinkPad X1 Carbon', '联想商务笔记本，轻薄便携', 9999.00, 60, '电脑', 'ThinkPad_X1_Carbon.jpg', '2025-12-30 02:36:32.0'),
    (5, 'AirPods Pro 2', '苹果无线降噪耳机', 1899.00, 150, '耳机', 'AirPods_Pro_2.jpg', '2025-12-30 02:36:32.0'),
    (6, 'Sony WH-1000XM5', '索尼降噪耳机，音质出色', 2999.00, 70, '耳机', 'Sony_WH-1000XM5.jpg', '2025-12-30 02:36:32.0'),
    (7, 'iPad Air 6', '11英寸全面屏，搭载A17 Pro芯片，支持Apple Pencil Pro', 5999.00, 80, '平板', 'iPad_Air_6.jpg', '2025-12-30 02:36:32.0'),
    (8, '华为MatePad Pro 12.6', '12.6英寸OLED屏幕，麒麟9000E芯片，支持鸿蒙系统', 4999.00, 60, '平板', '__MatePad_Pro_12.6.jpg', '2025-12-30 02:36:32.0'),
    (9, '小米Pad 7 Pro', '11.2英寸2.8K OLED屏幕，骁龙8 Gen 3芯片，支持小米灵感触控笔', 3299.00, 100, '平板', '__Pad_7_Pro.jpg', '2025-12-30 02:36:32.0'),
    (10, '三星Galaxy Tab S10+', '12.4英寸AMOLED屏幕，骁龙8 Gen 2芯片，支持S Pen', 5699.00, 70, '平板', '__Galaxy_Tab_S10_.jpg', '2025-12-30 02:36:32.0'),
    (11, '联想小新Pad Pro 14', '14英寸2.8K OLED屏幕，骁龙8 Gen 2芯片，轻薄便携', 3499.00, 90, '平板', '____Pad_Pro_14.jpg', '2025-12-30 02:36:32.0'),
    (12, 'Apple Watch Series 10', 'S9芯片，体温检测，血氧监测，心率检测', 2999.00, 120, '智能手表', 'Apple_Watch_Series_10.jpg', '2025-12-30 02:36:32.0'),
    (13, '华为Watch GT 5', '两周超长续航，ECG心电分析，血氧监测，支持鸿蒙系统', 1699.00, 148, '智能手表', '__Watch_GT_5.jpg', '2025-12-30 02:36:32.0'),
    (14, '小米Watch S3', '骁龙W5+芯片，1.43英寸AMOLED屏幕，支持独立通话', 1299.00, 180, '智能手表', '__Watch_S3.jpg', '2025-12-30 02:36:32.0'),
    (15, '三星Galaxy Watch 7', 'One UI Watch 5，旋转表圈，身体成分分析', 2499.00, 100, '智能手表', '__Galaxy_Watch_7.webp', '2025-12-30 02:36:32.0'),
    (16, 'Garmin Forerunner 965', '专业跑步手表，AMOLED屏幕，多卫星定位', 4299.00, 60, '智能手表', 'Garmin_Forerunner_965.jpg', '2025-12-30 02:36:32.0'),
    (17, '佳能EOS R5', '4500万像素，8K视频录制，机身防抖', 22999.00, 40, '相机', '__EOS_R5.jpg', '2025-12-30 02:36:32.0'),
    (18, '索尼A7 IV', '3300万像素，4K 60p视频，实时眼部对焦', 16999.00, 50, '相机', '__A7_IV.jpg', '2025-12-30 02:36:32.0'),
    (19, '尼康Z8', '4571万像素，8K 30p视频，机身防抖', 27999.00, 35, '相机', '__Z8.jpg', '2025-12-30 02:36:32.0'),
    (20, '富士X-T5', '4020万像素，4K 60p视频，复古外观设计', 13999.00, 60, '相机', '__X-T5.jpg', '2025-12-30 02:36:32.0'),
    (21, '松下Lumix S5 II', '2420万像素，4K 60p视频，相位对焦', 12999.00, 55, '相机', '__Lumix_S5_II.jpg', '2025-12-30 02:36:32.0'),
    (22, 'HomePod 2', '苹果智能音箱，空间音频，Siri语音助手', 2299.00, 90, '音箱', 'HomePod_2.jpg', '2025-12-30 02:36:32.0'),
    (23, '华为Sound X', '帝瓦雷联合设计，360°环绕声，鸿蒙系统', 1999.00, 110, '音箱', '__Sound_X.jpg', '2025-12-30 02:36:32.0'),
    (24, '小米Sound Pro', 'Hi-Fi音质，360°声场，小爱同学', 999.00, 150, '音箱', '__Sound_Pro.jpg', '2025-12-30 02:36:32.0'),
    (25, '索尼SRS-RA5000', '360°环绕声，LDAC编码，防水设计', 3299.00, 70, '音箱', '__SRS-RA5000.jpg', '2025-12-30 02:36:32.0'),
    (26, 'Bose Home Speaker 500', '全音域扬声器，内置Alexa，Wi-Fi连接', 3499.00, 65, '音箱', 'Bose_Home_Speaker_500.jpg', '2025-12-30 02:36:32.0'),
    (27, 'PlayStation 5 Pro', '索尼次世代游戏机，8K输出，3D音效', 5999.00, 80, '游戏机', 'PlayStation_5_Pro.jpg', '2025-12-30 02:36:32.0'),
    (28, 'Xbox Series X', '微软游戏机，4K 120fps，杜比视界', 4299.00, 100, '游戏机', 'Xbox_Series_X.jpg', '2025-12-30 02:36:32.0'),
    (29, 'Nintendo Switch OLED', '任天堂游戏机，7英寸OLED屏幕，便携设计', 2599.00, 120, '游戏机', 'Nintendo_Switch_OLED.jpg', '2025-12-30 02:36:32.0'),
    (30, 'Steam Deck', 'Valve掌机，支持Steam游戏库', 3999.00, 90, '游戏机', 'Steam_Deck.jpg', '2025-12-30 02:36:32.0'),
    (31, '罗技G923赛车方向盘', '力反馈，PS5/Xbox Series兼容', 2299.00, 75, '外设配件', '__G923_____.jpg', '2025-12-30 02:36:32.0'),
    (32, '小米智能门锁Pro', '3D结构光人脸识别，指纹解锁，远程控制', 2499.00, 100, '智能家居', '______Pro.jpg', '2025-12-30 02:36:32.0'),
    (33, '华为智能摄像头', '4K超清，AI人形检测，双向通话', 499.00, 180, '智能家居', '_______.jpg', '2025-12-30 02:36:32.0'),
    (34, '天猫精灵CC10', '10英寸触控屏，视频通话，智能家居控制', 599.00, 149, '智能家居', '____CC10.jpg', '2025-12-30 02:36:32.0'),
    (35, '绿米Aqara智能窗帘电机', '静音设计，APP控制，支持HomeKit', 899.00, 120, '智能家居', '__Aqara______.jpg', '2025-12-30 02:36:32.0'),
    (36, '海尔智能空调', '语音控制，自清洁，节能省电', 3699.00, 80, '办公设备', '______.jpg', '2025-12-30 02:36:32.0'),
    (37, '罗技MX Master 3S鼠标', '人体工学设计，MagSpeed滚轮，多设备连接', 899.00, 150, '外设配件', '__MX_Master_3S__.jpg', '2025-12-30 02:36:32.0'),
    (38, '华为MateBook E Go', '二合一笔记本，骁龙8cx Gen 3芯片，支持手写笔', 5999.00, 70, '手机', '__MateBook_E_Go.jpg', '2025-12-30 02:36:32.0'),
    (39, '爱普生L3256打印机', '墨仓式设计，无线连接，彩色打印', 1299.00, 100, '办公设备', '___L3256___.jpg', '2025-12-30 02:36:32.0'),
    (40, '得力A3激光打印机', '高速打印，自动双面，网络连接', 2999.00, 60, '办公设备', '__A3_____.jpg', '2025-12-30 02:36:32.0'),
    (41, '联想ThinkVision P27h-30显示器', '27英寸4K IPS屏幕，Type-C连接，HDR10', 2499.00, 79, '办公设备', '__ThinkVision_P27h-30___.jpg', '2025-12-30 02:36:32.0'),
    (42, '小米14 Pro', '骁龙8 Gen 3芯片，徕卡影像，120W快充', 4999.00, 120, '手机', '__14_Pro.jpg', '2025-12-30 02:36:32.0'),
    (43, '荣耀Magic6 Pro', '骁龙8 Gen 3芯片，MagicOS 7.0，5000mAh电池', 4599.00, 100, '手机', '__Magic6_Pro.jpg', '2025-12-30 02:36:32.0'),
    (44, '一加12', '骁龙8 Gen 3芯片，哈苏影像，150W快充', 4299.00, 110, '手机', '__12.jpg', '2025-12-30 02:36:32.0'),
    (45, 'vivo X100 Pro', '天玑9300芯片，蔡司影像，100W快充', 4999.00, 90, '手机', 'vivo_X100_Pro.jpg', '2025-12-30 02:36:32.0'),
    (46, '联想拯救者Y9000P 2025', '酷睿i9-14900HX，RTX 4080，2.5K 240Hz屏幕', 14999.00, 60, '电脑', '_____Y9000P_2025.jpg', '2025-12-30 02:36:32.0'),
    (47, '小米笔记本Pro X 16', '酷睿i7-14700H，RTX 4060，3.2K 165Hz屏幕', 9999.00, 80, '电脑', '_____Pro_X_16.jpg', '2025-12-30 02:36:32.0'),
    (48, '华为MateBook 16s', '酷睿i7-13700H，2.5K 120Hz屏幕，触控设计', 7999.00, 100, '电脑', '__MateBook_16s.jpg', '2025-12-30 02:36:32.0'),
    (49, '华硕ROG幻16', 'AMD R9-7945HX，RTX 4070，2.5K 240Hz屏幕', 13999.00, 69, '电脑', '__ROG_16.jpg', '2025-12-30 02:36:32.0'),
    (50, '小米Buds 4 Pro', '主动降噪，Hi-Fi音质，无线充电', 999.00, 150, '耳机', '__Buds_4_Pro.jpg', '2025-12-30 02:36:32.0'),
    (51, '华为FreeBuds Pro 3', '空间音频，主动降噪，无线充电', 1299.00, 118, '耳机', '__FreeBuds_Pro_3.jpg', '2025-12-30 02:36:32.0'),
    (52, 'vivo TWS 3 Pro', '主动降噪，无损音质，无线充电', 899.00, 125, '耳机', 'vivo_TWS_3_Pro.jpg', '2025-12-30 02:36:32.0');

-- 5. 插入数据到表: orders
INSERT INTO orders VALUES
    (1, 3, '2025-12-29 20:50:51', 899.00, 'pending', 'SCUT', '微信支付'),
    (2, 3, '2025-12-29 22:07:58', 2198.00, 'pending', 'SCUT', '微信支付'),
    (3, 3, '2025-12-30 09:56:59', 899.00, 'pending', 'SCUT', '微信支付'),
    (4, 3, '2025-12-30 02:37:37', 15497.00, 'pending', 'SCUT', '微信支付'),
    (5, 3, '2025-12-30 02:44:12', 899.00, 'pending', 'SCUT', '微信支付'),
    (6, 3, '2025-12-30 02:53:59', 899.00, 'pending', 'SCUT', '微信支付'),
    (7, 3, '2025-12-30 03:04:02', 899.00, 'pending', 'SCUT', '微信支付'),
    (8, 3, '2025-12-30 03:18:12', 899.00, 'pending', 'SCUT', '微信支付'),
    (9, 3, '2025-12-30 03:33:18', 899.00, 'pending', 'SCUT', '微信支付'),
    (10, 3, '2025-12-30 11:45:19', 899.00, 'pending', 'SCUT', '微信支付'),
    (11, 3, '2025-12-30 12:00:23', 1299.00, 'delivered', 'SCUT', '微信支付'),
    (12, 3, '2025-12-30 23:21:58', 2499.00, 'delivered', 'SCUT', '微信支付'),
    (13, 3, '2025-12-31 12:26:26', 899.00, 'pending', 'SCUT', '支付宝'),
    (14, 3, '2025-12-31 13:19:23', 3398.00, 'shipped', 'SCUT', '银行卡支付');

-- 5. 插入数据到表: shopping_cart
INSERT INTO shopping_cart VALUES
    (2, 2, 49, 2, '2025-12-30 02:45:01.0'),
    (3, 2, 48, 3, '2025-12-30 02:45:08.0'),
    (23, 3, 30, 1, '2025-12-31 21:30:29.0'),
    (24, 1, 52, 1, '2025-12-31 21:31:33.0');

-- 5. 插入数据到表: order_items
INSERT INTO order_items VALUES
    (2, 2, 51, 1, 1299.00),
    (3, 2, 52, 1, 899.00),
    (5, 4, 34, 1, 599.00),
    (6, 4, 49, 1, 13999.00),
    (7, 4, 52, 1, 899.00),
    (12, 9, 52, 1, 899.00),
    (13, 10, 52, 1, 899.00),
    (14, 11, 51, 1, 1299.00),
    (15, 12, 41, 1, 2499.00),
    (16, 13, 52, 1, 899.00),
    (17, 14, 13, 2, 1699.00);
