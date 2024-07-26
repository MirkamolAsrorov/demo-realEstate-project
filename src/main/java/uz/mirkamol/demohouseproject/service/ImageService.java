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
            if (mFile.isEmpty()) {
                return "Please select a file to upload";
            }
            var image = Images.builder()
                    .name(mFile.getOriginalFilename())
                    .type(mFile.getContentType())
                    .imageData(mFile.getBytes())
                    .build();

            imageRepo.save(image);

            return "Image uploaded successfully";
        }


    public byte[] getImage(String fileName) {
        var image = imageRepo.findByName(fileName);
        return image.map(Images::getImageData).orElse(null);
    }
}


