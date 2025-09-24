package org.example;

/**
 * 主应用类，包含main方法，负责程序的入口和基本流程控制
 */
public class WatermarkApplication {

    /**
     * 程序入口点
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.start();
    }
}