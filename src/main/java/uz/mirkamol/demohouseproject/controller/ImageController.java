package uz.mirkamol.demohouseproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mirkamol.demohouseproject.service.ImageService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> save(@RequestParam("image") MultipartFile mFile) throws IOException {
        var response = imageService.upload(mFile);

        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/getFileByName/{name}")
    public ResponseEntity<byte[]> getFileByName(@PathVariable String name) {
        byte[] imageData = imageService.getImage(name);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }
}
