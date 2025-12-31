### 设置Tomcat为Windows服务的具体命令

请以**管理员身份**打开PowerShell，然后执行以下命令：

1. **进入Tomcat的bin目录**：
   ```powershell
   Set-Location -Path "C:\Tomcat\bin"
   ```

2. **安装Tomcat服务**：
   ```powershell
   .\service.bat install
   ```

3. **将Tomcat服务设置为自动启动**（假设服务名称为Tomcat9，根据实际Tomcat版本调整）：
   ```powershell
   sc.exe config "Tomcat9" start= auto
   ```

### 注意事项：
- 确保Tomcat已正确安装在`C:\Tomcat`目录下
- 服务名称可能因Tomcat版本不同而变化（如Tomcat10对应服务名"Tomcat10"）
- 所有命令必须以**管理员身份**执行，否则安装服务会失败
- 安装成功后，可以在Windows「服务」管理界面中查看并验证Tomcat服务状态