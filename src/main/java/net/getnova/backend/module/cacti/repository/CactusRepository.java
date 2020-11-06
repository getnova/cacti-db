package net.getnova.backend.module.cacti.repository;

import net.getnova.backend.module.cacti.model.Cactus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CactusRepository extends JpaRepository<Cactus, UUID> {

  List<Cactus> findAllByOrderByNumber();

  Optional<Cactus> findByNumber(String number);
}
