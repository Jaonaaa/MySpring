package etu1915.framework.servlet;

import java.io.*;
import java.net.http.HttpResponse;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.util.*;

public class Upload {

    String name;
    String path;
    byte[] file;

    public static Upload uploadFile(HttpServletRequest request, HttpServlet servlet)
            throws Exception {

        String upload_directory = "uploads";
        String uploadPath = servlet.getServletContext().getRealPath("") + File.separator + upload_directory;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists())
            uploadDir.mkdir();

        Upload upload = new Upload();
        for (Part part : request.getParts()) {
            String fileName = getFileName(part);
            part.write(uploadPath + File.separator + fileName);
            upload = getFileData(uploadPath + File.separator + fileName, fileName);
        }
        return upload;
    }

    public static Upload getFileData(String pathFile, String filename) throws Exception {
        File file = new File(pathFile);
        Upload upload = new Upload();
        upload.setName(filename);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            upload.file = new byte[(int) file.length()];
            fis.read(upload.file);

        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return upload;
    }
    // fileName = part.getSubmittedFileName();

    private static String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
        }
        // return Constants.DEFAULT_FILENAME;
        return "x32.png";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
