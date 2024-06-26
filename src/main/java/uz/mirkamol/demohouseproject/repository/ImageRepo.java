package uz.mirkamol.demohouseproject.repository;

import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.mirkamol.demohouseproject.model.Images;

@Transactional
public interface ImageRepo extends JpaRepository<Images, Long> {
    Optional<Images> findByName(String fileName);
}
