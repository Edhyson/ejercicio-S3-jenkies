/**
 * 
 */
package com.edhy.api.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edhy.api.service.S3Service;

import software.amazon.awssdk.services.s3.model.S3Object;

/**
 * 
 */
@RestController
@RequestMapping("/files")
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam String key, @RequestParam String content) {
        s3Service.uploadFile(key, content);
        return "Archivo subido: " + key;
    }

    @GetMapping("/list")
    public List<String> list() {
        return s3Service.listFiles().stream()
                .map(S3Object::key)
                .toList();
    }
    
    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam String key) {
        byte[] data = s3Service.downloadFile(key);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + key + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }

}
