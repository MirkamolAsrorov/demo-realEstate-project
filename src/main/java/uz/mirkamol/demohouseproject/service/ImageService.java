package uz.mirkamol.demohouseproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.mirkamol.demohouseproject.model.Images;
import uz.mirkamol.demohouseproject.repository.ImageRepo;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepo imageRepo;

    public String upload(MultipartFile mFile) throws IOException {
        var image = Images.builder()
                .name(mFile.getOriginalFilename())
                .type(mFile.getContentType())
                .imageData(mFile.getBytes())
                .build();
        imageRepo.save(image);

        if (image.getId() != null) {
            return "Image uploaded successfully";
        }

        return "oops! something went wrong";
    }

    public byte[] getImage(String fileName) {
        var image = imageRepo.findByName(fileName);
        return image.map(Images::getImageData).orElse(null);
    }
}

