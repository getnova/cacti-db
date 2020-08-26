package net.getnova.backend.cacti.reposetories;

import net.getnova.backend.cacti.models.Genus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GenusRepository extends JpaRepository<Genus, UUID> {
}
