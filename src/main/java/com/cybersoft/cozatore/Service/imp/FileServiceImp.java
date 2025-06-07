package com.cybersoft.cozatore.Service.imp;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileServiceImp {
    void save(MultipartFile file);
    Resource load(String filename);
}
