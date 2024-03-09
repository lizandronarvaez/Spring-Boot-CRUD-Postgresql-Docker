package com.dashboard.dashboardinventario.uploads.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadService {
    String uploadFile(MultipartFile file, String namePath);

    String[] getFileNameParts(MultipartFile file);

    void verifyFileSize(MultipartFile file);

    void verifyFileFormat(String extFile);

    String generateNewFileName(String nameFile, String extFile);

    String getPathToSaveUploads(String namePath);

    void saveFile(MultipartFile file, String fileName, String directoryPath) throws IOException;
}
