package com.dashboard.dashboardinventario.uploads.service;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.*;

@Service
public class FileUploadServiceImpl implements IFileUploadService {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpeg", "jpg", "png");

    // Cargar de archivos segun el nombre de carpeta
    @Override
    public String uploadFile(MultipartFile file, String namePath) {
        try {
            // Nombre original del archivo y la extesion del archivo
            String[] fileNameParts = getFileNameParts(file);
            String nameFile = fileNameParts[0];
            String extFile = fileNameParts[1];

            // verifica el tamaño del archivo
            verifyFileSize(file);
            // Verrificamos el forma del archivo
            verifyFileFormat(extFile);

            // Generar el nombre del archivo nuevo
            String nameFileNew = generateNewFileName(nameFile, extFile);
            // Obtener la ruta de destino
            String pathSaveUploads = getPathToSaveUploads(namePath);
            System.out.println(pathSaveUploads);
            // Guardar el archivo en la ruta especificada
            saveFile(file, nameFileNew, pathSaveUploads);

            // Retorna el nombre del archivo
            return nameFileNew;

        } catch (IOException | MultipartException e) {
            return String.format("Error al cargar el archivo: " + e.getMessage(), e);
        }

    }

    @SuppressWarnings("null")
    @Override
    public String[] getFileNameParts(MultipartFile file) {
        String filenameOriginal = file.getOriginalFilename();
        return filenameOriginal.split("\\.");
    }

    @Override
    public void verifyFileSize(MultipartFile file) {
        long fileSize = file.getSize();
        if (fileSize > MAX_FILE_SIZE)
            throw new MaxUploadSizeExceededException(MAX_FILE_SIZE);
    }

    // Extensiones validas
    @Override
    public void verifyFileFormat(String extFile) {
        if (!ALLOWED_EXTENSIONS.contains(extFile.toLowerCase()))
            throw new MultipartException("¡El formato de archivo no es válido!");
    }

    @Override
    public String generateNewFileName(String nameFile, String extFile) {
        return nameFile + UUID.randomUUID().toString() + "." + extFile;
    }

    @Override
    public String getPathToSaveUploads(String namePath) {
        String currentDirectory = System.getProperty("user.dir");
        return currentDirectory +
                "/backend-dashboard-inventario/src/main/resources/static/uploads/" +
                namePath;
    }

    @Override
    public void saveFile(MultipartFile file, String fileName, String directoryPath) throws IOException {
        File folderUpload = new File(directoryPath);
        if (!folderUpload.exists()){
            folderUpload.mkdirs();
        }
        Path path = Paths.get(folderUpload + File.separator + fileName);
        Files.write(path, file.getBytes());
    }
}
