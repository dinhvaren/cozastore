// Service xử lý việc lưu trữ và quản lý file
package com.cybersoft.cozatore.Service;

import com.cybersoft.cozatore.Service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService implements FileServiceImp {
    // Lấy đường dẫn thư mục upload từ file cấu hình
    @Value("${root.path.upload}")
    private String rootPath;

    @Override
    public void save(MultipartFile file){
        // Tạo đối tượng Path từ đường dẫn
        Path path = Paths.get(rootPath);
        try {
            // Kiểm tra và tạo thư mục nếu chưa tồn tại
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            // Copy file vào thư mục đích, ghi đè nếu file đã tồn tại
            Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Error creating directory" + e.getMessage());
        }
    }
}
