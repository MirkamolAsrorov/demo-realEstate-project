package uz.mirkamol.demohouseproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mirkamol.demohouseproject.model.RealEstateAd;
import java.util.List;
import java.util.Optional;

public interface RealEstateAdRepo extends JpaRepository<RealEstateAd, Long> {
    List<RealEstateAd> findAllByOrderByPublicationDateDesc();
    Optional<RealEstateAd> findById(Long id);
}
