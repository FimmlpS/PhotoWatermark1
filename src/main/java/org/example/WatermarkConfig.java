package org.example;

import java.awt.*;

/**
 * 配置类，存储用户设置的水印参数
 * 包括字体大小、颜色、位置等信息
 */
public class WatermarkConfig {
    
    // 水印位置枚举
    public enum Position {
        TOP_LEFT, CENTER, BOTTOM_RIGHT
    }
    
    private int fontSize;
    private Color color;
    private Position position;
    private String fontName;
    
    /**
     * 构造函数，使用默认配置
     */
    public WatermarkConfig() {
        this.fontSize = 30;
        this.color = Color.WHITE;
        this.position = Position.BOTTOM_RIGHT;
        this.fontName = "Arial";
    }
    
    /**
     * 构造函数，使用自定义配置
     * @param fontSize 字体大小
     * @param color 字体颜色
     * @param position 水印位置
     */
    public WatermarkConfig(int fontSize, Color color, Position position) {
        this.fontSize = fontSize;
        this.color = color;
        this.position = position;
        this.fontName = "Arial";
    }
    
    /**
     * 获取字体大小
     * @return 字体大小
     */
    public int getFontSize() {
        return fontSize;
    }
    
    /**
     * 设置字体大小
     * @param fontSize 字体大小
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
    
    /**
     * 获取字体颜色
     * @return 字体颜色
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * 设置字体颜色
     * @param color 字体颜色
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
    /**
     * 获取水印位置
     * @return 水印位置
     */
    public Position getPosition() {
        return position;
    }
    
    /**
     * 设置水印位置
     * @param position 水印位置
     */
    public void setPosition(Position position) {
        this.position = position;
    }
    
    /**
     * 获取字体名称
     * @return 字体名称
     */
    public String getFontName() {
        return fontName;
    }
    
    /**
     * 设置字体名称
     * @param fontName 字体名称
     */
    public void setFontName(String fontName) {
        this.fontName = fontName;
    }
}