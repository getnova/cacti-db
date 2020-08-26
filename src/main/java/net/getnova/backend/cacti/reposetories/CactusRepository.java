package net.getnova.backend.cacti.reposetories;

import net.getnova.backend.cacti.models.Cactus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CactusRepository extends JpaRepository<Cactus, UUID> {

  Optional<Cactus> findByNumber(String number);
}
