package com.cybersoft.cozatore.Service.imp;

import org.springframework.web.multipart.MultipartFile;

public interface FileServiceImp {
    boolean save(MultipartFile file);
}
