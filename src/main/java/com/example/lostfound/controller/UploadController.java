package com.example.lostfound.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.nio.file.*;
import java.util.Map;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
public class UploadController {

    private static final String UPLOAD_DIR = "uploads";

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
            }

            // Ensure directory exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

            // Save file
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path target = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            // Build URL
            String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(filename)
                    .toUriString();

            return ResponseEntity.ok(Map.of("url", fileUrl));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", "Upload failed"));
        }
    }
}
