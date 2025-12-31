package com.shopping.util;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EncodingFilter implements Filter {
    
    private String encoding = "UTF-8";
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 从web.xml中获取编码配置，如果没有则使用默认UTF-8
        String configEncoding = filterConfig.getInitParameter("encoding");
        if (configEncoding != null && !configEncoding.isEmpty()) {
            encoding = configEncoding;
        }
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // 设置请求和响应的编码
        httpRequest.setCharacterEncoding(encoding);
        httpResponse.setCharacterEncoding(encoding);
        httpResponse.setContentType("text/html; charset=" + encoding);
        
        // 继续执行过滤器链
        chain.doFilter(httpRequest, httpResponse);
    }
    
    @Override
    public void destroy() {
        // 过滤器销毁时的操作
    }
}

