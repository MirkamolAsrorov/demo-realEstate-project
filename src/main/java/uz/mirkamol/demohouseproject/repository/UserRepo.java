package uz.mirkamol.demohouseproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mirkamol.demohouseproject.model.Users;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    Optional<Users> findById(Long id);
}
