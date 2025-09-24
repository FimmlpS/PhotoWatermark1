package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 核心处理类，负责读取图片、处理图片和保存结果
 * 包含水印添加的核心逻辑
 */
public class ImageProcessor {
    
    private ExifReader exifReader;
    private DirectoryManager directoryManager;
    
    public ImageProcessor() {
        this.exifReader = new ExifReader();
        this.directoryManager = new DirectoryManager();
    }
    
    /**
     * 处理单个图片文件
     * @param filePath 图片文件路径
     * @param config 水印配置
     */
    public void processImage(String filePath, WatermarkConfig config) {
        try {
            // 读取图片
            BufferedImage image = ImageIO.read(new File(filePath));
            
            // 提取拍摄日期
            String dateText = exifReader.extractDate(filePath);
            
            if (dateText != null) {
                // 添加水印
                BufferedImage watermarkedImage = addWatermark(image, dateText, config);
                
                // 保存处理后的图片
                saveWatermarkedImage(watermarkedImage, filePath);
                System.out.println("已成功处理图片: " + filePath);
            } else {
                System.out.println("无法提取拍摄日期信息: " + filePath);
            }
            
        } catch (IOException e) {
            System.err.println("处理图片时出错: " + e.getMessage());
        }
    }
    
    /**
     * 向图片添加水印
     * @param image 原始图片
     * @param text 水印文本
     * @param config 水印配置
     * @return 添加水印后的图片
     */
    private BufferedImage addWatermark(BufferedImage image, String text, WatermarkConfig config) {
        BufferedImage watermarkedImage = new BufferedImage(
                image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2d = watermarkedImage.createGraphics();
        // 绘制原始图片
        g2d.drawImage(image, 0, 0, null);
        
        // 设置水印样式
        g2d.setColor(config.getColor());
        g2d.setFont(new Font(config.getFontName(), Font.PLAIN, config.getFontSize()));
        
        // 计算水印位置
        FontMetrics metrics = g2d.getFontMetrics();
        int x, y;
        
        switch (config.getPosition()) {
            case TOP_LEFT:
                x = 10;
                y = metrics.getAscent() + 10;
                break;
            case CENTER:
                x = (image.getWidth() - metrics.stringWidth(text)) / 2;
                y = (image.getHeight() + metrics.getAscent() - metrics.getDescent()) / 2;
                break;
            case BOTTOM_RIGHT:
                x = image.getWidth() - metrics.stringWidth(text) - 10;
                y = image.getHeight() - metrics.getDescent() - 10;
                break;
            default:
                x = 10;
                y = metrics.getAscent() + 10;
        }
        
        // 绘制水印文本
        g2d.drawString(text, x, y);
        g2d.dispose();
        
        return watermarkedImage;
    }
    
    /**
     * 保存添加水印后的图片
     * @param image 添加水印后的图片
     * @param originalPath 原始图片路径
     * @throws IOException 保存图片时可能出现的异常
     */
    private void saveWatermarkedImage(BufferedImage image, String originalPath) throws IOException {
        Path originalFilePath = Paths.get(originalPath);
        String directory = originalFilePath.getParent().toString();
        String fileName = originalFilePath.getFileName().toString();
        
        // 创建输出目录
        String outputDir = directoryManager.createOutputDirectory(directory);
        
        // 构建输出文件路径
        String outputPath = outputDir + File.separator + fileName;
        
        // 获取文件扩展名
        String format = fileName.substring(fileName.lastIndexOf('.') + 1).toUpperCase();
        
        // 保存图片
        ImageIO.write(image, format, new File(outputPath));
    }
    
    /**
     * 批量处理目录中的所有图片
     * @param directoryPath 图片目录路径
     * @param config 水印配置
     */
    public void batchProcess(String directoryPath, WatermarkConfig config) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> {
            String lowerName = name.toLowerCase();
            return lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg") || lowerName.endsWith(".png") || lowerName.endsWith(".bmp");
        });
        
        if (files != null) {
            for (File file : files) {
                processImage(file.getAbsolutePath(), config);
            }
        }
    }
}