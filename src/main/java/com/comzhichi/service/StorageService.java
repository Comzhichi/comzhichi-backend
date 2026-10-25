
package com.comzhichi.service;
import com.comzhichi.exception.BusinessRuleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.UUID;

@Service
public class StorageService {

    private static final long MAX_SIZE = 5 * 1024 * 1024;

    private static final Map<String, String> TYPES = Map.of(
            "image/jpeg", ".jpg",
            "image/png", ".png",
            "image/webp", ".webp"
    );

    private final RestClient restClient = RestClient.create();
    private final String url;
    private final String secretKey;
    private final String bucket;

    public StorageService(
            @Value("${app.supabase.url}") String url,
            @Value("${app.supabase.key}") String secretKey,
            @Value("${app.supabase.bucket}") String bucket) {
        this.url = url.replaceAll("/+$", "");
        this.secretKey = secretKey;
        this.bucket = bucket;
    }

    public String upload(MultipartFile file) {
        String type = file.getContentType();
        String extension = type == null ? null : TYPES.get(type);

        if (file.isEmpty() || extension == null) {
            throw new BusinessRuleException(
                    "Solo se permiten imágenes JPG, PNG o WEBP"
            );
        }

        if (file.getSize() > MAX_SIZE) {
            throw new BusinessRuleException(
                    "La imagen no puede pesar más de 5 MB"
            );
        }

        String fileName = UUID.randomUUID() + extension;

        try {
            restClient.post()
                    .uri(URI.create(
                            url + "/storage/v1/object/" + bucket + "/" + fileName
                    ))
                    .header("apikey", secretKey)
                    .contentType(MediaType.parseMediaType(type))
                    .body(file.getBytes())
                    .retrieve()
                    .toBodilessEntity();

        } catch (IOException | RestClientException e) {
            throw new IllegalStateException("No se pudo subir la imagen", e);
        }

        return url + "/storage/v1/object/public/" + bucket + "/" + fileName;
    }
}