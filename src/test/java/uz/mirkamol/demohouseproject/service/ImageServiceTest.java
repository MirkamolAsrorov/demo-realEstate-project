package uz.mirkamol.demohouseproject.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import uz.mirkamol.demohouseproject.model.Images;
import uz.mirkamol.demohouseproject.repository.ImageRepo;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @InjectMocks
    private ImageService underTest;

    @Mock
    private ImageRepo imageRepo;


    @Test
    void upload() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", "Hello, World!".getBytes());

        when(imageRepo.save(any(Images.class))).thenReturn(new Images());

        String response = underTest.upload(file);

        verify(imageRepo).save(any(Images.class));
        assertEquals("Image uploaded successfully", response);
        assertThat(response).isNotNull();
    }

    @Test
    void testUploadEmptyFile() throws IOException {
        // Create a mock multipart file with no content
        MockMultipartFile file = new MockMultipartFile("file", "", "", new byte[0]);

        // Call the upload method
        String result = underTest.upload(file);

        // Assert that the result is as expected
        assertEquals("Please select a file to upload", result);
    }


    @Test
    void getImage() {
        Images image = new Images();
        image.setName("image.jpg");
        image.setImageData("image data".getBytes());

        when(imageRepo.findByName(image.getName())).thenReturn(Optional.of(image));
        byte[] underTestImage = underTest.getImage(image.getName());

        verify(imageRepo).findByName(image.getName());
        assertThat(image).isNotNull();

        assertEquals(image.getImageData(), underTestImage);
    }

    @Test
    void getImageNotFound() {
        when(imageRepo.findByName("image.jpg")).thenReturn(Optional.empty());

        byte[] underTestImage = underTest.getImage("image.jpg");

        verify(imageRepo).findByName("image.jpg");
        assertNull(underTestImage);
    }

}