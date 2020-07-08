package net.getnova.backend.cacti.reposetories;

import net.getnova.backend.cacti.models.Genus;
import net.getnova.backend.sql.reposetory.SqlRepository;

import java.util.UUID;

public interface GenusRepository extends SqlRepository<Genus, UUID> {
}
