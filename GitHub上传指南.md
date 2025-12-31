# GitHub上传指南

## 1. 创建GitHub仓库

### 步骤1：登录GitHub账号
- 打开GitHub网站（https://github.com）
- 登录你的GitHub账号

### 步骤2：创建新仓库
- 点击右上角的"+"号，选择"New repository"
- 填写仓库名称（例如：shopping-website）
- 填写仓库描述（可选）
- 选择仓库类型：Public（公开）或Private（私有）
- 不要勾选"Initialize this repository with a README"，因为我们已经在本地创建了README文件
- 点击"Create repository"

### 步骤3：获取远程仓库地址
- 创建仓库后，你将看到仓库的详细页面
- 在"Quick setup"部分，复制HTTPS或SSH地址（例如：https://github.com/your-username/shopping-website.git）

## 2. 连接本地仓库与GitHub仓库

### 步骤1：添加远程仓库
在命令行中执行以下命令，将本地仓库与GitHub仓库连接：
```bash
git remote add origin https://github.com/your-username/shopping-website.git
```

### 步骤2：推送代码到GitHub
执行以下命令，将本地代码推送到GitHub仓库：
```bash
git push -u origin master
```

### 步骤3：验证推送结果
- 刷新GitHub仓库页面
- 检查是否所有文件都已成功上传
- 查看提交记录是否包含"Initial commit"

## 3. 后续操作

### 拉取更新
当GitHub仓库有更新时，使用以下命令拉取到本地：
```bash
git pull origin master
```

### 推送新提交
1. 添加修改的文件：`git add .`
2. 提交修改：`git commit -m "提交信息"`
3. 推送代码：`git push origin master`

## 4. 常见问题

### 推送失败（权限问题）
- 确保你有权限推送该仓库
- 检查远程仓库地址是否正确
- 如果使用HTTPS，可能需要输入GitHub账号密码或个人访问令牌
- 如果使用SSH，确保你的SSH密钥已添加到GitHub账号

### 推送失败（分支不存在）
- 确保本地分支名称与远程分支名称一致
- 如果远程没有master分支，可能需要使用`git push -u origin main`（GitHub默认分支可能是main）

### 推送失败（冲突）
- 先拉取远程更新：`git pull origin master`
- 解决冲突后重新提交：`git add . && git commit -m "解决冲突"`
- 再次推送：`git push origin master`