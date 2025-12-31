# 查看Tomcat日志指南

## 1. 连接云服务器

首先，你需要通过SSH连接到云服务器。你可以使用以下工具：

- **Windows**：使用PowerShell或PuTTY
- **macOS/Linux**：使用终端

### 使用PowerShell连接

```powershell
ssh username@8.148.155.248
```

### 使用PuTTY连接

1. 下载并安装PuTTY
2. 打开PuTTY，输入主机名：`8.148.155.248`
3. 点击"Open"，输入用户名和密码

## 2. 查看Tomcat日志

Tomcat日志通常位于`$CATALINA_HOME/logs`目录下，其中`$CATALINA_HOME`是Tomcat的安装目录，通常是`/usr/local/tomcat`或`/opt/tomcat`。

### 2.1 查看catalina.out日志

`catalina.out`是Tomcat的主要日志文件，包含了启动和运行时的所有输出：

```bash
cd /usr/local/tomcat/logs
cat catalina.out
```

或者使用`tail`命令查看最新的日志：

```bash
tail -f catalina.out
```

### 2.2 查看访问日志

访问日志记录了所有HTTP请求，包括请求路径、状态码和响应时间：

```bash
cat localhost_access_log.$(date +%Y-%m-%d).txt
```

### 2.3 查看localhost日志

`localhost.YYYY-MM-DD.log`包含了本地主机相关的日志：

```bash
cat localhost.$(date +%Y-%m-%d).log
```

## 3. 使用浏览器开发者工具

你也可以使用浏览器的开发者工具来查看网络请求和响应：

1. 打开浏览器，访问出现问题的页面（如注册、查看订单详情或编辑商品）
2. 按F12打开开发者工具
3. 切换到"网络"或"Network"标签
4. 刷新页面，查看所有HTTP请求
5. 查看请求的状态码、请求URL和响应内容

## 4. 分析错误信息

### 4.1 常见错误类型

- **404 Not Found**：请求的资源不存在，可能是URL路径错误或资源未部署
- **500 Internal Server Error**：服务器内部错误，通常是代码中的异常
- **302 Found**：重定向，可能是重定向路径错误

### 4.2 查看具体错误

在`catalina.out`中查找包含"ERROR"或"Exception"的行，这些通常是导致问题的原因。例如：

```
SEVERE: Servlet.service() for servlet [RegisterServlet] in context with path [/shopping] threw exception
java.lang.NullPointerException
    at com.shopping.controller.RegisterServlet.doGet(RegisterServlet.java:20)
```

这表示在`RegisterServlet.java`的第20行发生了空指针异常。

## 5. 检查应用日志

如果你的应用有自己的日志系统，你需要查看应用的日志文件。通常，应用日志位于Tomcat的`webapps`目录下你的应用目录中，或者在`$CATALINA_HOME/logs`目录下。

## 6. 常见问题排查

### 6.1 404错误

- 检查请求URL是否正确
- 检查web.xml中的Servlet映射是否正确
- 检查JSP文件是否存在于正确的位置
- 检查WAR文件是否正确部署

### 6.2 500错误

- 查看catalina.out中的具体异常信息
- 检查代码中的逻辑错误
- 检查数据库连接是否正常
- 检查依赖库是否正确

### 6.3 重定向错误

- 检查重定向路径是否正确
- 检查Context Path是否正确配置
- 检查重定向使用的是绝对路径还是相对路径

## 7. 示例：查看注册页面跳转错误

1. 连接到云服务器
2. 查看访问日志，找到注册页面的请求：
   ```bash
grep "/shopping/register" localhost_access_log.$(date +%Y-%m-%d).txt
   ```
3. 查看catalina.out中的错误信息：
   ```bash
grep -A 10 -B 5 "RegisterServlet" catalina.out
   ```
4. 使用浏览器开发者工具查看网络请求，检查状态码和响应内容

通过以上步骤，你应该能够找到导致跳转错误的具体原因，进而解决问题。

## 8. 联系方式

如果遇到问题，你可以联系我，我会帮助你分析日志并解决问题。