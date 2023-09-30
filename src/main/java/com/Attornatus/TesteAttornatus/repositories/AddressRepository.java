package com.Attornatus.TesteAttornatus.repositories;

import com.Attornatus.TesteAttornatus.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByPublicPlace(String publicPlace);
}
