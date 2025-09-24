package org.example;

import java.awt.*;
import java.util.Scanner;

/**
 * 用户交互类，负责接收用户输入和显示信息
 * 可以是命令行界面或简单的图形界面
 */
public class UserInterface {
    
    private ImageProcessor imageProcessor;
    private DirectoryManager directoryManager;
    
    public UserInterface() {
        this.imageProcessor = new ImageProcessor();
        this.directoryManager = new DirectoryManager();
    }
    
    /**
     * 启动用户界面
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("==== 图片水印工具 ====");
        
        // 获取用户输入的图片文件路径
        System.out.print("请输入图片文件路径或目录路径: ");
        String path = scanner.nextLine();
        
        // 验证路径是否存在
        if (!directoryManager.isFile(path) && !directoryManager.isDirectory(path)) {
            System.out.println("错误: 指定的路径不存在!");
            scanner.close();
            return;
        }
        
        // 获取用户设置的水印参数
        WatermarkConfig config = getWatermarkConfigFromUser(scanner);
        
        // 处理图片
        System.out.println("开始处理图片...");
        long startTime = System.currentTimeMillis();
        
        if (directoryManager.isDirectory(path)) {
            // 批量处理目录中的图片
            imageProcessor.batchProcess(path, config);
        } else {
            // 处理单个图片文件
            imageProcessor.processImage(path, config);
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("图片处理完成，共耗时: " + (endTime - startTime) + "毫秒");
        
        scanner.close();
    }
    
    /**
     * 从用户输入获取水印配置
     * @param scanner 扫描器对象
     * @return 水印配置对象
     */
    private WatermarkConfig getWatermarkConfigFromUser(Scanner scanner) {
        WatermarkConfig config = new WatermarkConfig();
        
        System.out.println("\n请设置水印参数（直接按Enter键使用默认值）:");
        
        // 获取字体大小
        System.out.print("字体大小 (默认: " + config.getFontSize() + "): ");
        String fontSizeInput = scanner.nextLine();
        if (!fontSizeInput.trim().isEmpty()) {
            try {
                config.setFontSize(Integer.parseInt(fontSizeInput));
            } catch (NumberFormatException e) {
                System.out.println("无效的字体大小，将使用默认值。");
            }
        }
        
        // 获取字体颜色
        System.out.print("字体颜色 (1:黑色, 2:白色, 3:红色, 4:蓝色, 默认:白色): ");
        String colorInput = scanner.nextLine();
        if (!colorInput.trim().isEmpty()) {
            switch (colorInput.trim()) {
                case "1":
                    config.setColor(Color.BLACK);
                    break;
                case "2":
                    config.setColor(Color.WHITE);
                    break;
                case "3":
                    config.setColor(Color.RED);
                    break;
                case "4":
                    config.setColor(Color.BLUE);
                    break;
                default:
                    System.out.println("无效的颜色选项，将使用默认值。");
            }
        }
        
        // 获取水印位置
        System.out.print("水印位置 (1:左上角, 2:居中, 3:右下角, 默认:右下角): ");
        String positionInput = scanner.nextLine();
        if (!positionInput.trim().isEmpty()) {
            switch (positionInput.trim()) {
                case "1":
                    config.setPosition(WatermarkConfig.Position.TOP_LEFT);
                    break;
                case "2":
                    config.setPosition(WatermarkConfig.Position.CENTER);
                    break;
                case "3":
                    config.setPosition(WatermarkConfig.Position.BOTTOM_RIGHT);
                    break;
                default:
                    System.out.println("无效的位置选项，将使用默认值。");
            }
        }
        
        return config;
    }
}