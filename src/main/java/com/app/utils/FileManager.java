package com.app.utils;

import com.app.exceptions.MyException;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileManager {
    public static String getCatalogPath() {
        final String IMG_PATH = "\\src\\main\\resources\\static\\img\\";
        return System.getProperty("user.dir") + IMG_PATH;
    }

    public static String createFilename(MultipartFile multipartFile) {
        String[] arr = multipartFile.getOriginalFilename().split("\\.");
        String fileExtension = arr[arr.length - 1];

        String filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));

        return filename + "." + fileExtension;
    }

    public static String addFileToResources(MultipartFile multipartFile) {
        try {
            String catalogPath = getCatalogPath();
            String filename = createFilename(multipartFile);
            String fullFilename = catalogPath + filename;
            FileCopyUtils.copy(multipartFile.getBytes(), new File(fullFilename));
            return filename;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("ADD FILE EXCEPTION", LocalDateTime.now());
        }
    }
}
