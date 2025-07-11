package com.cybersoft.cozatore.Controller;

import com.cybersoft.cozatore.Service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileServiceImp  fileServiceImp;
    @GetMapping("/{filename}")
    ResponseEntity<?>  downLoadFile(@PathVariable String filename) {
        Resource resource = fileServiceImp.load(filename);
    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
            .body(resource);
    }
}
