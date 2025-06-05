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

@Service
public class FileService implements FileServiceImp {
    @Value("${root.path.upload}")
    private String rootPath;

    @Override
    public boolean save(MultipartFile file){
        Path path = Paths.get(rootPath);
        try {
           if (!Files.exists(path)) {
               Files.createDirectories(path);
           }
       } catch (Exception e) {
            throw new RuntimeException("Error creating directory" + e.getMessage());
        }
        return false;
    }
}
