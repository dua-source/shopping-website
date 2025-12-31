-- 新增产品数据
-- 使用shoppingdb数据库
USE shoppingdb;

-- 平板品类
INSERT INTO products (name, description, price, stock, category, image) VALUES
('iPad Air 6', '11英寸全面屏，搭载A17 Pro芯片，支持Apple Pencil Pro', 5999.00, 80, '平板', 'default.jpg'),
('华为MatePad Pro 12.6', '12.6英寸OLED屏幕，麒麟9000E芯片，支持鸿蒙系统', 4999.00, 60, '平板', 'default.jpg'),
('小米Pad 7 Pro', '11.2英寸2.8K OLED屏幕，骁龙8 Gen 3芯片，支持小米灵感触控笔', 3299.00, 100, '平板', 'default.jpg'),
('三星Galaxy Tab S10+', '12.4英寸AMOLED屏幕，骁龙8 Gen 2芯片，支持S Pen', 5699.00, 70, '平板', 'default.jpg'),
('联想小新Pad Pro 14', '14英寸2.8K OLED屏幕，骁龙8 Gen 2芯片，轻薄便携', 3499.00, 90, '平板', 'default.jpg');

-- 智能手表品类
INSERT INTO products (name, description, price, stock, category, image) VALUES
('Apple Watch Series 10', 'S9芯片，体温检测，血氧监测，心率检测', 2999.00, 120, '智能手表', 'default.jpg'),
('华为Watch GT 5', '两周超长续航，ECG心电分析，血氧监测，支持鸿蒙系统', 1699.00, 150, '智能手表', 'default.jpg'),
('小米Watch S3', '骁龙W5+芯片，1.43英寸AMOLED屏幕，支持独立通话', 1299.00, 180, '智能手表', 'default.jpg'),
('三星Galaxy Watch 7', 'One UI Watch 5，旋转表圈，身体成分分析', 2499.00, 100, '智能手表', 'default.jpg'),
('Garmin Forerunner 965', '专业跑步手表，AMOLED屏幕，多卫星定位', 4299.00, 60, '智能手表', 'default.jpg');

-- 相机品类
INSERT INTO products (name, description, price, stock, category, image) VALUES
('佳能EOS R5', '4500万像素，8K视频录制，机身防抖', 22999.00, 40, '相机', 'default.jpg'),
('索尼A7 IV', '3300万像素，4K 60p视频，实时眼部对焦', 16999.00, 50, '相机', 'default.jpg'),
('尼康Z8', '4571万像素，8K 30p视频，机身防抖', 27999.00, 35, '相机', 'default.jpg'),
('富士X-T5', '4020万像素，4K 60p视频，复古外观设计', 13999.00, 60, '相机', 'default.jpg'),
('松下Lumix S5 II', '2420万像素，4K 60p视频，相位对焦', 12999.00, 55, '相机', 'default.jpg');

-- 音箱品类
INSERT INTO products (name, description, price, stock, category, image) VALUES
('HomePod 2', '苹果智能音箱，空间音频，Siri语音助手', 2299.00, 90, '音箱', 'default.jpg'),
('华为Sound X', '帝瓦雷联合设计，360°环绕声，鸿蒙系统', 1999.00, 110, '音箱', 'default.jpg'),
('小米Sound Pro', 'Hi-Fi音质，360°声场，小爱同学', 999.00, 150, '音箱', 'default.jpg'),
('索尼SRS-RA5000', '360°环绕声，LDAC编码，防水设计', 3299.00, 70, '音箱', 'default.jpg'),
('Bose Home Speaker 500', '全音域扬声器，内置Alexa，Wi-Fi连接', 3499.00, 65, '音箱', 'default.jpg');

-- 游戏设备品类
INSERT INTO products (name, description, price, stock, category, image) VALUES
('PlayStation 5 Pro', '索尼次世代游戏机，8K输出，3D音效', 5999.00, 80, '游戏设备', 'default.jpg'),
('Xbox Series X', '微软游戏机，4K 120fps，杜比视界', 4299.00, 100, '游戏设备', 'default.jpg'),
('Nintendo Switch OLED', '任天堂游戏机，7英寸OLED屏幕，便携设计', 2599.00, 120, '游戏设备', 'default.jpg'),
('Steam Deck', 'Valve掌机，支持Steam游戏库', 3999.00, 90, '游戏设备', 'default.jpg'),
('罗技G923赛车方向盘', '力反馈，PS5/Xbox Series兼容', 2299.00, 75, '游戏设备', 'default.jpg');

-- 智能家居品类
INSERT INTO products (name, description, price, stock, category, image) VALUES
('小米智能门锁Pro', '3D结构光人脸识别，指纹解锁，远程控制', 2499.00, 100, '智能家居', 'default.jpg'),
('华为智能摄像头', '4K超清，AI人形检测，双向通话', 499.00, 180, '智能家居', 'default.jpg'),
('天猫精灵CC10', '10英寸触控屏，视频通话，智能家居控制', 599.00, 150, '智能家居', 'default.jpg'),
('绿米Aqara智能窗帘电机', '静音设计，APP控制，支持HomeKit', 899.00, 120, '智能家居', 'default.jpg'),
('海尔智能空调', '语音控制，自清洁，节能省电', 3699.00, 80, '智能家居', 'default.jpg');

-- 办公设备品类
INSERT INTO products (name, description, price, stock, category, image) VALUES
('罗技MX Master 3S鼠标', '人体工学设计，MagSpeed滚轮，多设备连接', 899.00, 150, '办公设备', 'default.jpg'),
('华为MateBook E Go', '二合一笔记本，骁龙8cx Gen 3芯片，支持手写笔', 5999.00, 70, '办公设备', 'default.jpg'),
('爱普生L3256打印机', '墨仓式设计，无线连接，彩色打印', 1299.00, 100, '办公设备', 'default.jpg'),
('得力A3激光打印机', '高速打印，自动双面，网络连接', 2999.00, 60, '办公设备', 'default.jpg'),
('联想ThinkVision P27h-30显示器', '27英寸4K IPS屏幕，Type-C连接，HDR10', 2499.00, 80, '办公设备', 'default.jpg');

-- 增加现有品类的产品
-- 手机品类
INSERT INTO products (name, description, price, stock, category, image) VALUES
('小米14 Pro', '骁龙8 Gen 3芯片，徕卡影像，120W快充', 4999.00, 120, '手机', 'default.jpg'),
('荣耀Magic6 Pro', '骁龙8 Gen 3芯片，MagicOS 7.0，5000mAh电池', 4599.00, 100, '手机', 'default.jpg'),
('一加12', '骁龙8 Gen 3芯片，哈苏影像，150W快充', 4299.00, 110, '手机', 'default.jpg'),
('vivo X100 Pro', '天玑9300芯片，蔡司影像，100W快充', 4999.00, 90, '手机', 'default.jpg');

-- 电脑品类
INSERT INTO products (name, description, price, stock, category, image) VALUES
('联想拯救者Y9000P 2025', '酷睿i9-14900HX，RTX 4080，2.5K 240Hz屏幕', 14999.00, 60, '电脑', 'default.jpg'),
('小米笔记本Pro X 16', '酷睿i7-14700H，RTX 4060，3.2K 165Hz屏幕', 9999.00, 80, '电脑', 'default.jpg'),
('华为MateBook 16s', '酷睿i7-13700H，2.5K 120Hz屏幕，触控设计', 7999.00, 100, '电脑', 'default.jpg'),
('华硕ROG幻16', 'AMD R9-7945HX，RTX 4070，2.5K 240Hz屏幕', 13999.00, 70, '电脑', 'default.jpg');

-- 耳机品类
INSERT INTO products (name, description, price, stock, category, image) VALUES
('小米Buds 4 Pro', '主动降噪，Hi-Fi音质，无线充电', 999.00, 150, '耳机', 'default.jpg'),
('华为FreeBuds Pro 3', '空间音频，主动降噪，无线充电', 1299.00, 120, '耳机', 'default.jpg'),
('vivo TWS 3 Pro', '主动降噪，无损音质，无线充电', 899.00, 130, '耳机', 'default.jpg'),
('一加Buds Pro 2', '主动降噪，哈苏音质，无线充电', 899.00, 110, '耳机', 'default.jpg');

-- 查看产品总数
SELECT COUNT(*) AS total_products FROM products;