package org.example;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 专门负责读取图片的EXIF信息
 * 提取拍摄日期并格式化
 */
public class ExifReader {
    
    /**
     * 从图片文件中提取拍摄日期
     * @param filePath 图片文件路径
     * @return 格式化后的日期字符串（YYYY-MM-DD格式），如果无法提取则返回null
     */
    public String extractDate(String filePath) {
        try {
            File file = new File(filePath);
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            
            // 获取EXIF子IFD目录，这里包含了相机拍摄信息
            ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            
            if (directory != null) {
                // 尝试获取拍摄日期时间
                Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                
                if (date != null) {
                    // 格式化日期为YYYY-MM-DD格式
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    return sdf.format(date);
                }
                
                // 如果没有找到原始日期时间，尝试其他可能的日期标签
                for (Tag tag : directory.getTags()) {
                    if (tag.getTagName().contains("Date") || tag.getTagName().contains("DateTime")) {
                        String dateString = tag.getDescription();
                        if (dateString != null && !dateString.isEmpty()) {
                            try {
                                // 尝试解析常见的EXIF日期格式
                                SimpleDateFormat exifFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                                Date parsedDate = exifFormat.parse(dateString);
                                SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
                                return targetFormat.format(parsedDate);
                            } catch (ParseException e) {
                                // 如果解析失败，尝试其他格式
                                try {
                                    SimpleDateFormat alternativeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date parsedDate = alternativeFormat.parse(dateString);
                                    SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    return targetFormat.format(parsedDate);
                                } catch (ParseException ex) {
                                    // 如果所有解析都失败，忽略并继续
                                }
                            }
                        }
                    }
                }
            }
            
        } catch (ImageProcessingException | IOException e) {
            System.err.println("读取EXIF信息时出错: " + e.getMessage());
        }
        
        // 如果无法提取日期信息，返回null
        return null;
    }
}