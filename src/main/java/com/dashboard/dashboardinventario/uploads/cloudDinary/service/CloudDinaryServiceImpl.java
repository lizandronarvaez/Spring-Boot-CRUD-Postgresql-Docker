package com.dashboard.dashboardinventario.uploads.cloudDinary.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;

@Service
public class CloudDinaryServiceImpl implements CloudDinaryService {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpeg", "jpg", "png", "webp");

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file, String nameFolder) {
        try {
            // Nombre original del archivo y la extesion del archivo
            String[] fileNameParts = getFileNameParts(file);
            String extFile = fileNameParts[1];
            // verifica el tamaño del archivo
            verifyFileSize(file);
            // Verrificamos el forma del archivo
            verifyFileFormat(extFile);

            // Guardar el archivo en clouddinary
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", nameFolder);
            @SuppressWarnings("rawtypes")
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);

            String url = (String) uploadedFile.get("url");
            return url;
        } catch (MultipartException | IOException e) {
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

    @Override
    public void verifyFileFormat(String extFile) {
        if (!ALLOWED_EXTENSIONS.contains(extFile.toLowerCase()))
            throw new MultipartException("¡El formato de archivo no es válido!");
    }

    @Override
    public String generateNewFileName(String nameFile, String extFile) {
        return nameFile + UUID.randomUUID().toString() + "." + extFile;
    }

}
