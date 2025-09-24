# 图片水印工具

这是一个基于Java开发的图片水印工具，可以自动读取图片的EXIF信息，提取拍摄日期，并根据用户设置将日期水印添加到图片上。

## 功能特点

- 自动读取图片的EXIF信息，提取拍摄日期
- 支持用户自定义水印的字体大小、颜色和位置
- 支持批量处理目录中的所有图片
- 将处理后的图片保存在原目录名_watermark的新目录下

## 技术栈

- Java 21
- Maven
- metadata-extractor库（用于读取EXIF信息）

## 项目结构

```
src/main/java/org/example/
├── WatermarkApplication.java  # 主应用类
├── ImageProcessor.java        # 核心处理类
├── ExifReader.java            # EXIF信息读取类
├── WatermarkConfig.java       # 配置类
├── DirectoryManager.java      # 目录管理类
└── UserInterface.java         # 用户交互类
```

## 使用方法

1. 确保已安装JDK 21和Maven
2. 克隆或下载项目源码
3. 进入项目目录，执行以下命令构建项目：
   ```
   mvn clean package
   ```
4. 运行生成的jar文件：
   ```
   java -jar target/PhotoWatermark1-1.0-SNAPSHOT.jar
   ```
5. 按照提示输入图片文件路径或目录路径
6. 设置水印参数（字体大小、颜色、位置）
7. 等待程序处理完成，处理后的图片将保存在原目录名_watermark的子目录中

## 水印参数设置

- **字体大小**：默认30
- **字体颜色**：
  - 1: 黑色
  - 2: 白色
  - 3: 红色
  - 4: 蓝色
- **水印位置**：
  - 1: 左上角
  - 2: 居中
  - 3: 右下角

## 注意事项

- 目前仅支持常见的图片格式（JPG、JPEG、PNG、BMP）
- 图片必须包含EXIF信息才能提取拍摄日期
- 如果无法提取EXIF信息，程序会跳过该图片并显示提示信息

## 依赖库说明

- **metadata-extractor**：用于读取图片的EXIF信息，版本2.18.0

## 扩展建议

- 支持更多图片格式
- 添加图形用户界面
- 支持自定义水印文本
- 支持批量处理多个目录