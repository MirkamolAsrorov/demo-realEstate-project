package uz.mirkamol.demohouseproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mirkamol.demohouseproject.model.Address;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
