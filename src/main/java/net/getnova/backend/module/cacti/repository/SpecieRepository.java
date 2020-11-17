package net.getnova.backend.module.cacti.repository;

import net.getnova.backend.module.cacti.model.Specie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpecieRepository extends JpaRepository<Specie, UUID> {

  List<Specie> findAllByOrderByName();
}