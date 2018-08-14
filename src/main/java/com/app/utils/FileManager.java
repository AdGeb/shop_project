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

            if (multipartFile == null || multipartFile.getBytes().length == 0) {
                throw new IllegalArgumentException("MULTIPART EXCEPTION");
            }

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

    public static void updateFileInResources(MultipartFile multipartFile, String filename) {
        try {

            if (multipartFile == null || multipartFile.getBytes().length == 0) {
                return;
            }

            String catalogPath = getCatalogPath();
            String fullFilename = catalogPath + filename;
            FileCopyUtils.copy(multipartFile.getBytes(), new File(fullFilename));
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("UPDATE FILE EXCEPTION", LocalDateTime.now());
        }
    }

    public static void removeFileFromResources(String filename) {
        try {
            String catalogPath = getCatalogPath();
            String fullFilename = catalogPath + filename;
            new File(fullFilename).delete();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("REMOVE FILE EXCEPTION", LocalDateTime.now());
        }
    }
}
