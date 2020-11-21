package net.getnova.module.cacti.repository;

import net.getnova.module.cacti.model.Genus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GenusRepository extends JpaRepository<Genus, UUID> {

  List<Genus> findAllByOrderByName();
}
