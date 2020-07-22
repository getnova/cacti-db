package net.getnova.backend.cacti.reposetories;

import net.getnova.backend.cacti.models.Specie;
import net.getnova.backend.sql.repository.SqlRepository;

import java.util.UUID;

public interface SpecieRepository extends SqlRepository<Specie, UUID> {
}
