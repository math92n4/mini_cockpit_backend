package com.example.mini_cockpit_backend.service.file;

import com.example.mini_cockpit_backend.api.dto.IvsrDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileService {

    File multipartToFile(MultipartFile multipartFile) throws IOException;
    List<IvsrDTO> parseFile(String path) throws FileNotFoundException;
}
