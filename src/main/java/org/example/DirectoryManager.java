package org.example;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 目录管理类，负责创建和管理输出目录
 * 处理文件路径相关操作
 */
public class DirectoryManager {
    
    private static final String OUTPUT_DIR_SUFFIX = "_watermark";
    
    /**
     * 创建输出目录
     * @param originalDirectory 原始目录路径
     * @return 创建的输出目录路径
     */
    public String createOutputDirectory(String originalDirectory) {
        Path originalPath = Paths.get(originalDirectory);
        String parentDir = originalPath.getParent() != null ? originalPath.getParent().toString() : ".";
        String dirName = originalPath.getFileName().toString() + OUTPUT_DIR_SUFFIX;
        
        // 构建输出目录路径
        String outputDir = parentDir + File.separator + dirName;
        
        // 创建目录
        File directory = new File(outputDir);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new RuntimeException("无法创建输出目录: " + outputDir);
            }
            System.out.println("已创建输出目录: " + outputDir);
        }
        
        return outputDir;
    }
    
    /**
     * 检查路径是否为目录
     * @param path 要检查的路径
     * @return 如果是目录返回true，否则返回false
     */
    public boolean isDirectory(String path) {
        File file = new File(path);
        return file.exists() && file.isDirectory();
    }
    
    /**
     * 检查路径是否为文件
     * @param path 要检查的路径
     * @return 如果是文件返回true，否则返回false
     */
    public boolean isFile(String path) {
        File file = new File(path);
        return file.exists() && file.isFile();
    }
    
    /**
     * 获取文件名（不包含扩展名）
     * @param filePath 文件路径
     * @return 文件名（不包含扩展名）
     */
    public String getFileNameWithoutExtension(String filePath) {
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }
    
    /**
     * 获取文件扩展名
     * @param filePath 文件路径
     * @return 文件扩展名（不包含点号）
     */
    public String getFileExtension(String filePath) {
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }
}