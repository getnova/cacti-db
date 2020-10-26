package net.getnova.backend.module.cacti.repositories;

import net.getnova.backend.module.cacti.models.Specie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpecieRepository extends JpaRepository<Specie, UUID> {

  List<Specie> findAllByOrderByName();
}
