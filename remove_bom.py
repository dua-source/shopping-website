#!/usr/bin/env python3

import os
import codecs

def remove_bom(file_path):
    """移除文件中的BOM字符"""
    with open(file_path, 'rb') as f:
        content = f.read()
    
    # 移除UTF-8 BOM
    if content.startswith(codecs.BOM_UTF8):
        content = content[len(codecs.BOM_UTF8):]
    
    with open(file_path, 'wb') as f:
        f.write(content)
    
    print(f"已移除文件 {file_path} 的BOM字符")

if __name__ == "__main__":
    # 修复EmailUtil.java
    email_util_path = "src\com\shopping\util\EmailUtil.java"
    remove_bom(email_util_path)