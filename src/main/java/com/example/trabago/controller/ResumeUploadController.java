package com.example.trabago.controller;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/resume")
public class ResumeUploadController {
    @PostMapping("/upload")

    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        // Check if the file is empty
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to upload file: File is empty.");
        }

        try {
            // Save the file to the server
            Path filePath = Path.of("E:\\TrabaGo\\trabago-backend\\src\\main\\resources\\resumes\\" + file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Check if the Python script call is necessary
            if (filePath.toString().endsWith(".pdf")) {
                // Call the Python script
                Map<String, String> result = callPythonScript(filePath.toString());
                JSONObject jsonResult = new JSONObject(result);
                return ResponseEntity.ok(jsonResult.toString());
            } else {
                return ResponseEntity.ok("File uploaded successfully, but no Python script was called.");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }

    private Map<String, String> callPythonScript(String filePath) {
        String pythonScriptPath = "E:\\TrabaGo\\trabago-backend\\resume-analyzer\\main.py";
        Map<String, String> output = new HashMap<>();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, filePath);
            Process process = processBuilder.start();

            // Read the output from the process
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] split = line.split(":");
                    if (Objects.equals(split[0], "Front_End_Developer")) {
                        split[0] = "Front-End Developer";
                    }
                    if (Objects.equals(split[0], "Project_manager")) {
                        split[0] = "Project Manager";
                    } else {
                        split[0] = split[0].replace("_", " ");
                    }

                    output.put(split[0], split[1]);
                }
            }

            // Wait for the process to finish and check the exit value
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Python script exited with code: " + exitCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to call Python script: " + e.getMessage(), e);
        }

        return output;
    }
}
